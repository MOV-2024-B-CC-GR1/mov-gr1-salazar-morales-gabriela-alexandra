package com.example.ccgr12024b_gasm.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * DatabaseHelper es una clase para manejar la base de datos SQLite.
 * Contiene las operaciones CRUD para las tablas Cliente y Pedido.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "AppCRUD.db"
        private const val DATABASE_VERSION = 2

        // Tablas
        const val TABLE_CLIENTE = "Cliente"
        const val TABLE_PEDIDO = "Pedido"

        // Campos de Cliente
        const val CLIENTE_ID = "id"
        const val CLIENTE_NOMBRE = "nombre"
        const val CLIENTE_EMAIL = "email"
        const val CLIENTE_TELEFONO = "telefono"
        const val CLIENTE_ACTIVO = "activo"
        const val CLIENTE_FECHA_REGISTRO = "fechaRegistro"
        const val CLIENTE_LATITUD = "latitud"
        const val CLIENTE_LONGITUD = "longitud"

        // Campos de Pedido
        const val PEDIDO_ID = "id"
        const val PEDIDO_CLIENTE_ID = "cliente_id"
        const val PEDIDO_DESCRIPCION = "descripcion"
        const val PEDIDO_ESTADO = "estado"
        const val PEDIDO_FECHA_PEDIDO = "fechaPedido"
    }

    /**
     * Se ejecuta al crear la base de datos por primera vez.
     * Aquí se crean las tablas Cliente y Pedido.
     */
    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla Cliente
        val createClienteTable = """
            CREATE TABLE $TABLE_CLIENTE (
                $CLIENTE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $CLIENTE_NOMBRE TEXT NOT NULL,
                $CLIENTE_EMAIL TEXT NOT NULL,
                $CLIENTE_TELEFONO TEXT NOT NULL,
                $CLIENTE_ACTIVO INTEGER NOT NULL,
                $CLIENTE_FECHA_REGISTRO TEXT NOT NULL,
                $CLIENTE_LATITUD REAL,
                $CLIENTE_LONGITUD REAL
            );
        """
        db.execSQL(createClienteTable)

        // Crear tabla Pedido
        val createPedidoTable = """
            CREATE TABLE $TABLE_PEDIDO (
                $PEDIDO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $PEDIDO_CLIENTE_ID INTEGER NOT NULL,
                $PEDIDO_DESCRIPCION TEXT NOT NULL,
                $PEDIDO_ESTADO TEXT NOT NULL,
                $PEDIDO_FECHA_PEDIDO TEXT NOT NULL,
                FOREIGN KEY ($PEDIDO_CLIENTE_ID) REFERENCES $TABLE_CLIENTE($CLIENTE_ID)
            );
        """
        db.execSQL(createPedidoTable)
    }

    /**
     * Se ejecuta cuando la base de datos necesita ser actualizada.
     * Elimina las tablas existentes y las recrea.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Si estamos actualizando desde la versión 1 a la 2 (agregando columnas de latitud y longitud)
        if (oldVersion == 1 && newVersion == 2) {
            try {
                // Intentar agregar las nuevas columnas a la tabla existente
                db.execSQL("ALTER TABLE $TABLE_CLIENTE ADD COLUMN $CLIENTE_LATITUD REAL;")
                db.execSQL("ALTER TABLE $TABLE_CLIENTE ADD COLUMN $CLIENTE_LONGITUD REAL;")
            } catch (e: Exception) {
                // Si algo sale mal, eliminar las tablas y recrearlas
                db.execSQL("DROP TABLE IF EXISTS $TABLE_PEDIDO")
                db.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTE")
                onCreate(db)
            }
        } else {
            // Para otros casos, mantener el comportamiento original
            db.execSQL("DROP TABLE IF EXISTS $TABLE_PEDIDO")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTE")
            onCreate(db)
        }
    }


    // --- CRUD para la tabla Cliente ---

    /**
     * Inserta un nuevo cliente en la tabla Cliente.
     * @param nombre Nombre del cliente.
     * @param email Correo electrónico del cliente.
     * @param telefono Teléfono del cliente.
     * @param activo Indica si el cliente está activo (true/false).
     * @param fechaRegistro Fecha de registro del cliente.
     * @return ID del cliente recién insertado.
     */
    fun insertarCliente(nombre: String, email: String, telefono: String, activo: Boolean,
                        fechaRegistro: String, latitud: Double?, longitud: Double?): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(CLIENTE_NOMBRE, nombre)
            put(CLIENTE_EMAIL, email)
            put(CLIENTE_TELEFONO, telefono)
            put(CLIENTE_ACTIVO, if (activo) 1 else 0)
            put(CLIENTE_FECHA_REGISTRO, fechaRegistro)
            put(CLIENTE_LATITUD, latitud)
            put(CLIENTE_LONGITUD, longitud)
        }
        return db.insert(TABLE_CLIENTE, null, values)
    }

    /**
     * Obtiene todos los clientes de la tabla Cliente.
     * @return Cursor con los resultados de la consulta.
     */
    fun obtenerClientes(): Cursor {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_CLIENTE, null, null, null, null, null, "$CLIENTE_NOMBRE ASC"
        )

        // Log para verificar los datos
        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(CLIENTE_NOMBRE))
                val latIndex = cursor.getColumnIndexOrThrow(CLIENTE_LATITUD)
                val longIndex = cursor.getColumnIndexOrThrow(CLIENTE_LONGITUD)
                val lat = if (!cursor.isNull(latIndex)) cursor.getDouble(latIndex) else null
                val long = if (!cursor.isNull(longIndex)) cursor.getDouble(longIndex) else null

                android.util.Log.d("DatabaseHelper", "Cliente en DB: $nombre - Lat: $lat, Long: $long")
            } while (cursor.moveToNext())
            cursor.moveToFirst() // Regresamos el cursor al inicio
        }

        return cursor
    }

    /**
     * Actualiza los datos de un cliente existente.
     * @param id ID del cliente a actualizar.
     * @param nombre Nuevo nombre del cliente.
     * @param email Nuevo correo electrónico del cliente.
     * @param telefono Nuevo teléfono del cliente.
     * @param activo Indica si el cliente está activo.
     * @return Número de filas afectadas.
     */
    fun actualizarCliente(id: Int, nombre: String, email: String, telefono: String,
                          activo: Boolean, latitud: Double?, longitud: Double?): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(CLIENTE_NOMBRE, nombre)
            put(CLIENTE_EMAIL, email)
            put(CLIENTE_TELEFONO, telefono)
            put(CLIENTE_ACTIVO, if (activo) 1 else 0)
            put(CLIENTE_LATITUD, latitud)
            put(CLIENTE_LONGITUD, longitud)
        }
        return db.update(TABLE_CLIENTE, values, "$CLIENTE_ID = ?", arrayOf(id.toString()))
    }

    /**
     * Elimina un cliente de la tabla Cliente.
     * @param id ID del cliente a eliminar.
     * @return Número de filas afectadas.
     */
    fun eliminarCliente(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_CLIENTE, "$CLIENTE_ID = ?", arrayOf(id.toString()))
    }

    // --- CRUD para la tabla Pedido ---

    /**
     * Inserta un nuevo pedido en la tabla Pedido.
     * @param clienteId ID del cliente asociado al pedido.
     * @param descripcion Descripción del pedido.
     * @param estado Estado del pedido.
     * @param fechaPedido Fecha del pedido.
     * @return ID del pedido recién insertado.
     */
    fun insertarPedido(clienteId: Int, descripcion: String, estado: String, fechaPedido: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(PEDIDO_CLIENTE_ID, clienteId)
            put(PEDIDO_DESCRIPCION, descripcion)
            put(PEDIDO_ESTADO, estado)
            put(PEDIDO_FECHA_PEDIDO, fechaPedido)
        }
        return db.insert(TABLE_PEDIDO, null, values)
    }

    /**
     * Obtiene todos los pedidos asociados a un cliente.
     * @param clienteId ID del cliente.
     * @return Cursor con los resultados de la consulta.
     */
    fun obtenerPedidosPorCliente(clienteId: Int): Cursor {
        val db = readableDatabase
        return db.query(
            TABLE_PEDIDO,
            null,
            "$PEDIDO_CLIENTE_ID = ?",
            arrayOf(clienteId.toString()),
            null,
            null,
            "$PEDIDO_FECHA_PEDIDO DESC"
        )
    }

    /**
     * Actualiza los datos de un pedido existente.
     * @param id ID del pedido a actualizar.
     * @param descripcion Nueva descripción del pedido.
     * @param estado Nuevo estado del pedido.
     * @return Número de filas afectadas.
     */
    fun actualizarPedido(id: Int, descripcion: String, estado: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(PEDIDO_DESCRIPCION, descripcion)
            put(PEDIDO_ESTADO, estado)
        }
        return db.update(TABLE_PEDIDO, values, "$PEDIDO_ID = ?", arrayOf(id.toString()))
    }

    /**
     * Elimina un pedido de la tabla Pedido.
     * @param id ID del pedido a eliminar.
     * @return Número de filas afectadas.
     */
    fun eliminarPedido(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_PEDIDO, "$PEDIDO_ID = ?", arrayOf(id.toString()))
    }
}
