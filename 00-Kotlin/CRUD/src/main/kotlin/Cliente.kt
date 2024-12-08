package org.example

import java.util.Date

data class Cliente(
    val id: Int,
    var nombre: String,
    var email: String,
    var telefono: String,
    var activo: Boolean,
    val fechaRegistro: Date = Date() // Fecha por defecto como la actual
)
