package com.example.ccgr12024b_gasm.data.model

/**
 * Representa un curso dentro de la aplicación, incluyendo detalles sobre el curso como su título,
 * instructor, duración, precio, y más.
 *
 * @property id El identificador único del curso.
 * @property title El título del curso.
 * @property instructor El nombre del instructor del curso.
 * @property duration La duración del curso (por ejemplo, "40 horas").
 * @property price El precio del curso en formato numérico (tipo [Double]).
 * @property rating La calificación del curso (en una escala de 0 a 5) en formato [Float].
 * @property imageUrl La URL de la imagen representativa del curso.
 * @property progress El progreso actual del curso (porcentaje completado, valor entre 0 y 100).
 * @property isFavorite Indica si el curso está marcado como favorito por el usuario. El valor predeterminado es `false`.
 * @property description Una descripción opcional del curso. El valor predeterminado es una cadena vacía.
 * @property modules Lista de módulos del curso. El valor predeterminado es una lista vacía, ya que algunos cursos pueden no tener módulos definidos.
 */

data class Course(
    val id: String,
    val title: String,
    val instructor: String,
    val duration: String,
    val price: Double,
    val rating: Float,
    val imageUrl: String,
    val progress: Int = 0,
    val isFavorite: Boolean = false,
    val description: String = "",
    val modules: List<Module> = emptyList()

)