package com.example.ccgr12024b_gasm.model

import java.io.Serializable
import java.util.Date

data class Cliente(
    val id: Int,
    var nombre: String,
    var email: String,
    var telefono: String,
    var activo: Boolean,
    val fechaRegistro: Date
) : Serializable

