package com.example.ccgr12024b_gasm.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ccgr12024b_gasm.data.model.Course
import com.example.ccgr12024b_gasm.data.model.User

/**
 * Ayuda a gestionar la base de datos para usuarios y cursos, creando tablas y proporcionando
 * métodos para interactuar con ellas.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "tech_academy.db"
        private const val DATABASE_VERSION = 3

        // Tabla Usuarios
        private const val TABLE_USERS = "users"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_PASSWORD = "password"

        // Tabla Cursos
        private const val TABLE_COURSES = "courses"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_INSTRUCTOR = "instructor"
        private const val COLUMN_DURATION = "duration"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_RATING = "rating"
        private const val COLUMN_IMAGE_URL = "image_url"
        private const val COLUMN_PROGRESS = "progress"

        // Query para crear la tabla usuarios
        private const val SQL_CREATE_USERS_TABLE = """
            CREATE TABLE IF NOT EXISTS $TABLE_USERS (
                $COLUMN_EMAIL TEXT PRIMARY KEY,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_PHONE TEXT NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL
            )
        """

        // Query para crear la tabla cursos
        private const val SQL_CREATE_COURSES_TABLE = """
            CREATE TABLE IF NOT EXISTS $TABLE_COURSES (
                $COLUMN_ID TEXT PRIMARY KEY,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_INSTRUCTOR TEXT NOT NULL,
                $COLUMN_DURATION TEXT NOT NULL,
                $COLUMN_PRICE REAL NOT NULL,
                $COLUMN_RATING REAL NOT NULL,
                $COLUMN_IMAGE_URL TEXT,
                $COLUMN_PROGRESS INTEGER DEFAULT 0
            )
        """
    }

    /**
     * Método llamado al crear la base de datos. Crea las tablas de usuarios y cursos.
     *
     * @param db La base de datos SQLite.
     */
    override fun onCreate(db: SQLiteDatabase) {
        try {
            println("DEBUG: Creando tablas de la base de datos")
            db.execSQL(SQL_CREATE_USERS_TABLE)
            db.execSQL(SQL_CREATE_COURSES_TABLE)
            println("DEBUG: Tablas creadas exitosamente")
            insertSampleCourses(db)
        } catch (e: Exception) {
            println("DEBUG: Error en onCreate: ${e.message}")
            e.printStackTrace()
        }
    }

    /**
     * Método que maneja la actualización de la base de datos (por ejemplo, cuando se incrementa la versión).
     *
     * @param db La base de datos SQLite.
     * @param oldVersion La versión antigua de la base de datos.
     * @param newVersion La versión nueva de la base de datos.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        println("DEBUG: Actualizando base de datos de versión $oldVersion a $newVersion")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COURSES")
        onCreate(db)
    }

    /**
     * Inserta cursos de ejemplo en la base de datos al crearla.
     *
     * @param db La base de datos SQLite.
     */
    private fun insertSampleCourses(db: SQLiteDatabase) {
        val sampleCourses = listOf(
            Course(
                id = "1",
                title = "Desarrollo Web Full Stack",
                instructor = "Carlos Guerra",
                duration = "60 horas",
                price = 49.99,
                rating = 4.7f,
                imageUrl = "",
                progress = 60
            ),
            Course(
                id = "2",
                title = "Python para Ciencia de Datos",
                instructor = "Alejandro Guerra",
                duration = "40 horas",
                price = 59.99,
                rating = 4.9f,
                imageUrl = "",
                progress = 0
            ),
            Course(
                id = "3",
                title = "DevOps y CI/CD",
                instructor = "Pedro Vivanco",
                duration = "45 horas",
                price = 79.99,
                rating = 4.8f,
                imageUrl = "",
                progress = 0
            )
        )

        sampleCourses.forEach { course ->
            val values = ContentValues().apply {
                put(COLUMN_ID, course.id)
                put(COLUMN_TITLE, course.title)
                put(COLUMN_INSTRUCTOR, course.instructor)
                put(COLUMN_DURATION, course.duration)
                put(COLUMN_PRICE, course.price)
                put(COLUMN_RATING, course.rating)
                put(COLUMN_IMAGE_URL, course.imageUrl)
                put(COLUMN_PROGRESS, course.progress)
            }
            db.insert(TABLE_COURSES, null, values)
        }
    }

    // Métodos para usuarios
    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param email El correo electrónico del nuevo usuario.
     * @param name El nombre del nuevo usuario.
     * @param phone El número de teléfono del nuevo usuario.
     * @param password La contraseña del nuevo usuario.
     * @return [Boolean] que indica si el registro fue exitoso.
     */
    fun registerUser(email: String, name: String, phone: String, password: String): Boolean {
        println("DEBUG: Iniciando proceso de registro")
        println("DEBUG: Datos recibidos -> Email: $email, Name: $name")

        val db = this.writableDatabase
        var success = false

        try {
            // Verificar si el usuario ya existe
            val checkCursor = db.rawQuery(
                "SELECT * FROM users WHERE email = ?",
                arrayOf(email)
            )

            if (checkCursor.moveToFirst()) {
                println("DEBUG: El email ya está registrado")
                checkCursor.close()
                return false
            }
            checkCursor.close()

            // Insertar el nuevo usuario
            val values = ContentValues().apply {
                put("email", email)
                put("name", name)
                put("phone", phone)
                put("password", password)
            }

            val id = db.insert("users", null, values)
            success = id != -1L
            println("DEBUG: Resultado de inserción: $id")

            // Verificar que se guardó correctamente
            if (success) {
                val verifyCursor = db.rawQuery(
                    "SELECT * FROM users WHERE email = ?",
                    arrayOf(email)
                )
                if (verifyCursor.moveToFirst()) {
                    println("DEBUG: Usuario verificado después de registro")
                } else {
                    println("DEBUG: ¡Error! Usuario no encontrado después de registro")
                    success = false
                }
                verifyCursor.close()
            }

        } catch (e: Exception) {
            println("DEBUG: Error durante registro: ${e.message}")
            e.printStackTrace()
            success = false
        }

        db.close()
        return success
    }

    /**
     * Verifica la estructura de la base de datos, revisando las tablas y columnas existentes.
     */
    fun verifyDatabaseStructure() {
        val db = this.readableDatabase
        println("DEBUG: Verificando estructura de la base de datos")

        try {
            // Verificar si la tabla users existe
            val tablesCursor = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='users'",
                null
            )

            if (tablesCursor.moveToFirst()) {
                println("DEBUG: Tabla 'users' existe")

                // Verificar las columnas de la tabla
                val columnsCursor = db.rawQuery("PRAGMA table_info(users)", null)
                println("DEBUG: Columnas en la tabla users:")
                while (columnsCursor.moveToNext()) {
                    val columnName = columnsCursor.getString(1)
                    println("DEBUG: - $columnName")
                }
                columnsCursor.close()

                // Contar usuarios
                val countCursor = db.rawQuery("SELECT COUNT(*) FROM users", null)
                if (countCursor.moveToFirst()) {
                    val count = countCursor.getInt(0)
                    println("DEBUG: Número total de usuarios: $count")
                }
                countCursor.close()

            } else {
                println("DEBUG: ¡ERROR! La tabla 'users' no existe")
            }
            tablesCursor.close()

        } catch (e: Exception) {
            println("DEBUG: Error verificando base de datos: ${e.message}")
            e.printStackTrace()
        }

        db.close()
    }

    /**
     * Inicia sesión con las credenciales del usuario.
     *
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return [Boolean] que indica si el login fue exitoso.
     */
    fun loginUser(email: String, password: String): Boolean {
        println("DEBUG: Iniciando proceso de login")
        println("DEBUG: Email recibido: $email")

        val db = this.readableDatabase
        var success = false

        try {
            // Primero verificamos si el usuario existe
            val cursor = db.rawQuery(
                "SELECT * FROM users WHERE email = ? AND password = ?",
                arrayOf(email, password)
            )

            success = cursor.moveToFirst()
            println("DEBUG: ¿Se encontró el usuario?: $success")

            if (success) {
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                println("DEBUG: Usuario encontrado: $name")
            } else {
                // Si el login falló, veamos si al menos existe el email
                val emailCheckCursor = db.rawQuery(
                    "SELECT * FROM users WHERE email = ?",
                    arrayOf(email)
                )
                val emailExists = emailCheckCursor.moveToFirst()
                println("DEBUG: ¿Existe el email?: $emailExists")
                emailCheckCursor.close()
            }

            cursor.close()
        } catch (e: Exception) {
            println("DEBUG: Error durante login: ${e.message}")
            e.printStackTrace()
            success = false
        }

        db.close()
        return success
    }

    /**
     * Obtiene un usuario por su correo electrónico.
     *
     * @param email El correo electrónico del usuario.
     * @return El objeto [User] si se encuentra, o null si no existe.
     */
    fun getUserByEmail(email: String): User? {
        var db: SQLiteDatabase? = null
        var cursor: android.database.Cursor? = null

        return try {
            db = this.readableDatabase
            cursor = db.query(
                TABLE_USERS,
                null,
                "$COLUMN_EMAIL = ?",
                arrayOf(email),
                null,
                null,
                null
            )

            if (cursor.moveToFirst()) {
                User(
                    email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                    password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
                )
            } else null
        } catch (e: Exception) {
            println("DEBUG: Error obteniendo usuario: ${e.message}")
            null
        } finally {
            cursor?.close()
            db?.close()
        }
    }

    /**
     * Listar todos los usuarios en la base de datos para depuración.
     */
    fun debugListUsers() {
        var db: SQLiteDatabase? = null
        var cursor: android.database.Cursor? = null

        try {
            db = this.readableDatabase
            println("DEBUG: ---- Lista de usuarios en la base de datos ----")

            cursor = db.query(TABLE_USERS, null, null, null, null, null, null)

            if (cursor.count == 0) {
                println("DEBUG: No hay usuarios en la base de datos")
            } else {
                while (cursor.moveToNext()) {
                    val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                    println("DEBUG: Usuario -> Email: $email, Name: $name")
                }
            }
        } catch (e: Exception) {
            println("DEBUG: Error listando usuarios: ${e.message}")
        } finally {
            cursor?.close()
            db?.close()
            println("DEBUG: ---- Fin de la lista de usuarios ----")
        }
    }

    /**
     * Limpia todas las tablas de la base de datos.
     */
    fun clearDatabase() {
        var db: SQLiteDatabase? = null
        try {
            db = this.writableDatabase
            db.beginTransaction()
            db.delete(TABLE_USERS, null, null)
            db.setTransactionSuccessful()
            println("DEBUG: Base de datos limpiada")
        } catch (e: Exception) {
            println("DEBUG: Error limpiando base de datos: ${e.message}")
        } finally {
            db?.endTransaction()
            db?.close()
        }
    }

    // Métodos para cursos
    /**
     * Obtiene todos los cursos disponibles en la base de datos.
     *
     * @return Lista de objetos [Course] con todos los cursos.
     */
    fun getAllCourses(): List<Course> {
        val courses = mutableListOf<Course>()
        var db: SQLiteDatabase? = null
        var cursor: android.database.Cursor? = null

        try {
            db = this.readableDatabase
            cursor = db.query(TABLE_COURSES, null, null, null, null, null, null)

            while (cursor.moveToNext()) {
                courses.add(
                    Course(
                        id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        instructor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INSTRUCTOR)),
                        duration = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURATION)),
                        price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
                        rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING)),
                        imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)),
                        progress = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PROGRESS))
                    )
                )
            }
        } catch (e: Exception) {
            println("DEBUG: Error obteniendo cursos: ${e.message}")
        } finally {
            cursor?.close()
            db?.close()
        }
        return courses
    }

    /**
     * Actualiza el progreso de un curso en la base de datos.
     *
     * @param courseId El ID del curso a actualizar.
     * @param progress El nuevo progreso del curso (porcentaje).
     */
    fun updateCourseProgress(courseId: String, progress: Int) {
        var db: SQLiteDatabase? = null
        try {
            db = this.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_PROGRESS, progress)
            }
            db.update(TABLE_COURSES, values, "$COLUMN_ID = ?", arrayOf(courseId))
        } catch (e: Exception) {
            println("DEBUG: Error actualizando progreso: ${e.message}")
        } finally {
            db?.close()
        }
    }

    /**
     * Obtiene un curso por su ID.
     *
     * @param courseId El ID del curso a obtener.
     * @return El objeto [Course] si se encuentra el curso, o null si no se encuentra.
     */
    fun getCourseById(courseId: String): Course? {
        var db: SQLiteDatabase? = null
        var cursor: android.database.Cursor? = null

        return try {
            db = this.readableDatabase
            cursor = db.query(
                TABLE_COURSES,
                null,
                "$COLUMN_ID = ?",
                arrayOf(courseId),
                null,
                null,
                null
            )

            if (cursor.moveToFirst()) {
                Course(
                    id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    instructor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INSTRUCTOR)),
                    duration = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURATION)),
                    price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
                    rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING)),
                    imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)),
                    progress = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PROGRESS))
                )
            } else null
        } catch (e: Exception) {
            println("DEBUG: Error obteniendo curso: ${e.message}")
            null
        } finally {
            cursor?.close()
            db?.close()
        }
    }

    /**
     * Actualiza los datos del usuario en la base de datos.
     *
     * @param user El objeto [User] con la información actualizada.
     */
    fun updateUser(user: User) {
        var db: SQLiteDatabase? = null
        try {
            db = this.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_NAME, user.name)
                put(COLUMN_PHONE, user.phone)
                // No actualizamos el email porque es la clave primaria
            }
            db.update(
                TABLE_USERS,
                values,
                "$COLUMN_EMAIL = ?",
                arrayOf(user.email)
            )
            println("DEBUG: Usuario actualizado exitosamente")
        } catch (e: Exception) {
            println("DEBUG: Error actualizando usuario: ${e.message}")
        } finally {
            db?.close()
        }
    }

    fun updatePassword(email: String, newPassword: String): Boolean {
        var db: SQLiteDatabase? = null
        try {
            db = this.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_PASSWORD, newPassword)
            }
            val rowsAffected = db.update(
                TABLE_USERS,
                values,
                "$COLUMN_EMAIL = ?",
                arrayOf(email)
            )
            println("DEBUG: Contraseña actualizada para el usuario: $email")
            return rowsAffected > 0
        } catch (e: Exception) {
            println("DEBUG: Error actualizando contraseña: ${e.message}")
            return false
        } finally {
            db?.close()
        }
    }
}