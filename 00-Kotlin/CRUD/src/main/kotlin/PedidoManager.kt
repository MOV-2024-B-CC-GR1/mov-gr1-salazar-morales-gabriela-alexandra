package org.example

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Types
import java.io.File
import java.util.*

class PedidoManager(private val archivo: File = File("pedidos.json")) {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(DateJsonAdapter())
        .build()

    private val tipo = Types.newParameterizedType(List::class.java, Pedido::class.java)
    private val jsonAdapter = moshi.adapter<List<Pedido>>(tipo)

    private var pedidos: MutableList<Pedido> = mutableListOf()

    init {
        cargarPedidos()
    }

    private fun cargarPedidos() {
        if (archivo.exists()) {
            val contenido = archivo.readText()
            pedidos = jsonAdapter.fromJson(contenido)?.toMutableList() ?: mutableListOf()
        }
    }

    private fun guardarPedidos() {
        archivo.writeText(jsonAdapter.toJson(pedidos))
    }

    fun agregarPedido(pedido: Pedido) {
        if (pedidos.any { it.id == pedido.id }) {
            println("Error: El ID del pedido ya existe.")
        } else {
            pedidos.add(pedido)
            guardarPedidos()
            println("Pedido agregado exitosamente.")
        }
    }

    fun listarPedidos() {
        if (pedidos.isEmpty()) {
            println("No hay pedidos registrados.")
        } else {
            pedidos.forEach { println(it) }
        }
    }

    fun actualizarPedido(id: Int, descripcion: String? = null, estado: String? = null) {
        val pedido = pedidos.find { it.id == id }
        if (pedido != null) {
            descripcion?.let { pedido.descripcion = it }
            estado?.let { pedido.estado = it }
            guardarPedidos()
            println("Pedido actualizado correctamente.")
        } else {
            println("Pedido no encontrado.")
        }
    }

    fun eliminarPedido(id: Int) {
        if (pedidos.removeIf { it.id == id }) {
            guardarPedidos()
            println("Pedido eliminado correctamente.")
        } else {
            println("Pedido no encontrado.")
        }
    }
}
