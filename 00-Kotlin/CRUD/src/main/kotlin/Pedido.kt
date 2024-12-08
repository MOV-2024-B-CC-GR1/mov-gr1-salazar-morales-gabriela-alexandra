package org.example

import java.util.Date

data class Pedido(
    val id: Int,
    var clienteId: Int, // Relación con Cliente
    var descripcion: String,
    var estado: String,
    val fechaPedido: Date = Date() // Fecha por defecto como la actual
)
