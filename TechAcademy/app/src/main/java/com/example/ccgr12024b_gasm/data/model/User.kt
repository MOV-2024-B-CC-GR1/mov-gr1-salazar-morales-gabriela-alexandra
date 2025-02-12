package com.example.ccgr12024b_gasm.data.model

/**
 * Representa a un usuario de la aplicación. Contiene los detalles personales y de autenticación
 * del usuario.
 *
 * @property email El correo electrónico del usuario. Es único y sirve como identificador principal.
 * @property name El nombre del usuario. Puede ser modificado.
 * @property phone El número de teléfono del usuario. Puede ser modificado.
 * @property password La contraseña del usuario, que se utiliza para la autenticación.
 */

data class User(
    val email: String,
    var name: String,
    var phone: String,
    val password: String
)