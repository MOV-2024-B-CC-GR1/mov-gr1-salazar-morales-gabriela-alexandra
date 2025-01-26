package com.example.ccgr12024b_gasm.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.ccgr12024b_gasm.database.DatabaseHelper
import com.example.ccgr12024b_gasm.model.Pedido
import java.text.SimpleDateFormat
import java.util.*

class PedidoDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // Insertar un pedido
    fun insertarPedido(pedido: Pedido): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("clienteId", pedido.clienteId)
            put("descripcion", pedido.descripcion)
            put("estado", pedido.estado)
            put("fechaPedido", dateFormat.format(pedido.fechaPedido))
        }
        val result = db.insert("Pedido", null, values)
        db.close()
        return result
    }


    // Actualizar un pedido
    fun actualizarPedido(pedido: Pedido): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("clienteId", pedido.clienteId)
            put("descripcion", pedido.descripcion)
            put("estado", pedido.estado)
            put("fechaPedido", dateFormat.format(pedido.fechaPedido))
        }
        val result = db.update("Pedido", values, "id = ?", arrayOf(pedido.id.toString()))
        db.close()
        return result
    }

    // Eliminar un pedido
    fun eliminarPedido(id: Int): Int {
        val db = dbHelper.writableDatabase
        val result = db.delete("Pedido", "id = ?", arrayOf(id.toString()))
        db.close()
        return result
    }

    // Obtener todos los pedidos
    fun obtenerTodos(): List<Pedido> {
        val pedidos = mutableListOf<Pedido>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Pedido", null)

        if (cursor.moveToFirst()) {
            do {
                try {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val clienteId = cursor.getInt(cursor.getColumnIndexOrThrow("clienteId"))
                    val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                    val estado = cursor.getString(cursor.getColumnIndexOrThrow("estado"))
                    val fechaPedido = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow("fechaPedido"))) ?: Date()

                    val pedido = Pedido(id, clienteId, descripcion, estado, fechaPedido)
                    pedidos.add(pedido)
                    println("Pedido cargado: $pedido") // Verificar datos cargados
                } catch (e: Exception) {
                    println("Error al cargar pedido: ${e.message}")
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return pedidos
    }

    fun obtenerPedidosPorCliente(clienteId: Int): List<Pedido> {
        val pedidos = mutableListOf<Pedido>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Pedido WHERE cliente_id = ?", arrayOf(clienteId.toString()))

        if (cursor.moveToFirst()) {
            do {
                try {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                    val estado = cursor.getString(cursor.getColumnIndexOrThrow("estado"))
                    val fechaPedido = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow("fechaPedido"))) ?: Date()

                    val pedido = Pedido(id, clienteId, descripcion, estado, fechaPedido)
                    pedidos.add(pedido)
                } catch (e: Exception) {
                    println("Error al cargar pedido: ${e.message}")
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return pedidos
    }



}
