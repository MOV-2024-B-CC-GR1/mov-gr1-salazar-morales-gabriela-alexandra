package com.example.ccgr12024b_gasm.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "CRUD.db"
        const val DATABASE_VERSION = 2

        const val TABLE_CLIENTE = "Cliente"
        const val TABLE_PEDIDO = "Pedido"
        const val TABLE_PEDIDO_TEMP = "Pedido_TEMP"  // Definir la constante para la tabla temporal

        const val CREATE_TABLE_CLIENTE = """
            CREATE TABLE $TABLE_CLIENTE (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                email TEXT NOT NULL,
                telefono TEXT NOT NULL,
                activo INTEGER NOT NULL,
                fechaRegistro TEXT NOT NULL
            );
        """

        const val CREATE_TABLE_PEDIDO = """
            CREATE TABLE $TABLE_PEDIDO (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                cliente_id INTEGER NOT NULL,
                descripcion TEXT NOT NULL,
                estado TEXT NOT NULL,
                fechaPedido TEXT NOT NULL,
                FOREIGN KEY(cliente_id) REFERENCES $TABLE_CLIENTE(id) ON DELETE CASCADE
            );
        """
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("PRAGMA foreign_keys=ON;") // Habilitar claves foráneas
        db?.execSQL(CREATE_TABLE_CLIENTE)
        db?.execSQL(CREATE_TABLE_PEDIDO)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            // Crear una tabla temporal con la nueva estructura
            val CREATE_TEMP_TABLE = """
            CREATE TABLE $TABLE_PEDIDO_TEMP (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                cliente_id INTEGER NOT NULL,
                descripcion TEXT NOT NULL,
                estado TEXT NOT NULL,
                fechaPedido TEXT NOT NULL,
                FOREIGN KEY(cliente_id) REFERENCES $TABLE_CLIENTE(id) ON DELETE CASCADE
            );
        """
            db?.execSQL(CREATE_TEMP_TABLE)

            // Copiar los datos existentes a la nueva tabla
            db?.execSQL("""
            INSERT INTO $TABLE_PEDIDO_TEMP (id, cliente_id, descripcion, estado, fechaPedido)
            SELECT id, cliente_id, descripcion, estado, fechaPedido FROM $TABLE_PEDIDO;
        """)

            // Eliminar la tabla original
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_PEDIDO")

            // Renombrar la tabla temporal
            db?.execSQL("ALTER TABLE $TABLE_PEDIDO_TEMP RENAME TO $TABLE_PEDIDO")
        }
    }

}
