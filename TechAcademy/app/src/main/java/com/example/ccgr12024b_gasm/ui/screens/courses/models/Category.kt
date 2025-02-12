package com.example.ccgr12024b_gasm.ui.screens.courses.models

/**
 * Representa una categoría dentro de los cursos, utilizada para agrupar y organizar cursos según su temática o área.
 * Cada categoría tiene un identificador único, un nombre y un ícono que la representa visualmente.
 *
 * @param id Identificador único de la categoría. Se utiliza para distinguir entre diferentes categorías.
 * @param name El nombre de la categoría, como "Desarrollo Web", "Data Science", etc.
 * @param iconRes Recurso de ícono asociado con la categoría, representado por un identificador de recurso de imagen.
 */
data class Category(
    val id: String,
    val name: String,
    val iconRes: Int
)
