package com.example.ccgr12024b_gasm.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.ccgr12024b_gasm.database.DatabaseHelper
import com.example.ccgr12024b_gasm.model.Cliente
import java.text.SimpleDateFormat
import java.util.*

class ClienteDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)
    private val db = dbHelper.writableDatabase
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun insertarCliente(cliente: Cliente): Long {
        val values = ContentValues().apply {
            put("nombre", cliente.nombre)
            put("email", cliente.email)
            put("telefono", cliente.telefono)
            put("activo", if (cliente.activo) 1 else 0)
            put("fechaRegistro", dateFormat.format(cliente.fechaRegistro))
        }
        return db.insert(DatabaseHelper.TABLE_CLIENTE, null, values)
    }

    fun obtenerTodos(): List<Cliente> {
        val clientes = mutableListOf<Cliente>()
        val cursor: Cursor = db.query(DatabaseHelper.TABLE_CLIENTE, null, null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                try {
                    val cliente = Cliente(
                        id = getInt(getColumnIndexOrThrow("id")),
                        nombre = getString(getColumnIndexOrThrow("nombre")),
                        email = getString(getColumnIndexOrThrow("email")),
                        telefono = getString(getColumnIndexOrThrow("telefono")),
                        activo = getInt(getColumnIndexOrThrow("activo")) == 1,
                        fechaRegistro = dateFormat.parse(getString(getColumnIndexOrThrow("fechaRegistro"))) ?: Date()
                    )
                    clientes.add(cliente)
                    // Log para depurar
                    println("Cliente cargado: $cliente")
                } catch (e: Exception) {
                    println("Error al cargar cliente: ${e.message}")
                }
            }
            close()
        }
        return clientes
    }

    fun actualizarCliente(cliente: Cliente): Int {
        val values = ContentValues().apply {
            put("nombre", cliente.nombre)
            put("email", cliente.email)
            put("telefono", cliente.telefono)
            put("activo", if (cliente.activo) 1 else 0)
            put("fechaRegistro", dateFormat.format(cliente.fechaRegistro)) // Asegúrate de actualizar fechaRegistro
        }
        return db.update(DatabaseHelper.TABLE_CLIENTE, values, "id=?", arrayOf(cliente.id.toString()))
    }

    fun eliminarCliente(id: Int): Int {
        return db.delete(DatabaseHelper.TABLE_CLIENTE, "id=?", arrayOf(id.toString()))
    }
}
