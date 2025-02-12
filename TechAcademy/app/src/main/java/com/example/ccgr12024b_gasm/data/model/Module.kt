package com.example.ccgr12024b_gasm.data.model

/**
 * Representa un módulo dentro de un curso. Un módulo contiene varias lecciones que cubren un tema específico
 * dentro del curso.
 *
 * @property id El identificador único del módulo.
 * @property title El título del módulo.
 * @property description Una descripción que explica el contenido o propósito del módulo.
 * @property lessons Una lista de lecciones que forman parte de este módulo.
 *                    Cada lección está representada como una cadena de texto que describe el título de la lección.
 */
data class Module(
    val id: String,
    val title: String,
    val description: String,
    val lessons: List<String>
)