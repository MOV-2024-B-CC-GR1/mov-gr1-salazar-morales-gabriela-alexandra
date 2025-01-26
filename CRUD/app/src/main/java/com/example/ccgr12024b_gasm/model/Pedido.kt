package com.example.ccgr12024b_gasm.model

import java.io.Serializable
import java.util.Date

data class Pedido(
    val id: Int = 0,               // ID único del pedido
    var clienteId: Int = 0,        // ID del cliente asociado
    var descripcion: String = "",  // Descripción del pedido
    var estado: String = "",       // Estado del pedido (ej: "Pendiente", "Completado")
    var fechaPedido: Date = Date() // Fecha del pedido
) : Serializable
