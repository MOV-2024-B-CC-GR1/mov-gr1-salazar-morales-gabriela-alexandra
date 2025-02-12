// CourseModules.kt
package com.example.ccgr12024b_gasm.data.model

/**
 * Representa un módulo dentro de un curso. Un módulo contiene lecciones que cubren un tema específico
 * relacionado con el curso en cuestión.
 *
 * @property id El identificador único del módulo.
 * @property title El título del módulo.
 * @property description Una descripción que explica el contenido o propósito del módulo.
 * @property lessons Una lista de lecciones que forman parte de este módulo.
 */
data class CourseModule(
    val id: String,
    val title: String,
    val description: String,
    val lessons: List<Lesson>
)

/**
 * Representa una lección dentro de un módulo. Cada lección tiene un título y su duración en minutos.
 *
 * @property title El título de la lección.
 * @property duration La duración de la lección en minutos.
 */
data class Lesson(
    val title: String,
    val duration: Int
)

/**
 * Obtiene los módulos de un curso según el ID proporcionado. Si el ID del curso es "1", retorna los módulos
 * correspondientes a un curso de desarrollo web. De lo contrario, retorna los módulos correspondientes a un curso
 * de Python y análisis de datos.
 *
 * @param courseId El identificador único del curso.
 * @return Una lista de módulos que corresponden al curso dado.
 */
fun getModulesForCourse(courseId: String): List<CourseModule> {
    return if (courseId == "1") {
        listOf(
            CourseModule(
                id = "frontend",
                title = "Frontend Essentials",
                description = "Desarrollo de interfaces modernas",
                lessons = listOf(
                    Lesson("HTML5 Semántico", 3),
                    Lesson("CSS Grid y Flexbox", 3),
                    Lesson("JavaScript Avanzado", 4)
                )
            ),
            CourseModule(
                id = "backend",
                title = "Backend Profesional",
                description = "Arquitectura de servidores y APIs",
                lessons = listOf(
                    Lesson("Node.js y Express", 4),
                    Lesson("Bases de Datos SQL", 3),
                    Lesson("APIs RESTful", 4)
                )
            )
        )
    } else {
        listOf(
            CourseModule(
                id = "python",
                title = "Fundamentos de Python",
                description = "Base sólida en programación",
                lessons = listOf(
                    Lesson("Introducción a Python", 3),
                    Lesson("Estructuras de Datos", 4),
                    Lesson("Funciones y Módulos", 3)
                )
            ),
            CourseModule(
                id = "data",
                title = "Análisis de Datos",
                description = "Procesamiento y visualización",
                lessons = listOf(
                    Lesson("Pandas y NumPy", 4),
                    Lesson("Visualización con Matplotlib", 3),
                    Lesson("Machine Learning Básico", 4)
                )
            )
        )
    }
}