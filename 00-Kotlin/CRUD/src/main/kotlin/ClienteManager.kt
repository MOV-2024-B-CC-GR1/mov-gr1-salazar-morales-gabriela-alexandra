package org.example

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Types
import java.io.File
import java.util.*

class ClienteManager(private val archivo: File = File("clientes.json")) {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(DateJsonAdapter())
        .build()

    private val tipo = Types.newParameterizedType(List::class.java, Cliente::class.java)
    private val jsonAdapter = moshi.adapter<List<Cliente>>(tipo)

    private var clientes: MutableList<Cliente> = mutableListOf() // Cambiar a mutableListOf

    init {
        cargarClientes()
    }

    private fun cargarClientes() {
        if (archivo.exists()) {
            val contenido = archivo.readText()
            clientes = jsonAdapter.fromJson(contenido)?.toMutableList() ?: mutableListOf()
        }
    }

    private fun guardarClientes() {
        archivo.writeText(jsonAdapter.toJson(clientes))
    }

    fun agregarCliente(cliente: Cliente) {
        if (clientes.any { it.id == cliente.id }) {
            println("Error: El ID del cliente ya existe.")
        } else {
            clientes.add(cliente)
            guardarClientes()
            println("Cliente agregado exitosamente.")
        }
    }

    fun listarClientes() {
        if (clientes.isEmpty()) {
            println("No hay clientes registrados.")
        } else {
            clientes.forEach { println(it) }
        }
    }

    fun actualizarCliente(id: Int, nombre: String? = null, email: String? = null, telefono: String? = null, activo: Boolean? = null) {
        val cliente = clientes.find { it.id == id }
        if (cliente != null) {
            println("Cliente encontrado: $cliente")

            // Actualizar solo si el campo no es nulo
            nombre?.let { cliente.nombre = it }
            email?.let { cliente.email = it }
            telefono?.let { cliente.telefono = it }
            activo?.let { cliente.activo = it }

            // Guardar los cambios
            guardarClientes()
            println("Cliente actualizado correctamente.")
        } else {
            println("Cliente no encontrado.")
        }
    }



    fun eliminarCliente(id: Int) {
        if (clientes.removeIf { it.id == id }) {
            guardarClientes()
            println("Cliente eliminado correctamente.")
        } else {
            println("Cliente no encontrado.")
        }
    }
}
