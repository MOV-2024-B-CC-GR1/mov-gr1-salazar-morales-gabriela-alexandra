package com.example.ccgr12024b_gasm.ui.screens.courses.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ccgr12024b_gasm.data.model.Course
import com.example.ccgr12024b_gasm.ui.navigation.Screen

/**
 * Componente que muestra una tarjeta (Card) con información de un curso.
 * El componente incluye detalles como el título del curso, el instructor, la duración, la calificación,
 * el precio, y un botón para inscribirse en el curso. Al hacer clic en el botón de inscripción,
 * el usuario es redirigido a la pantalla de inscripción del curso.
 *
 * @param course Un objeto de tipo [Course] que contiene los datos del curso a mostrar.
 * @param navController El controlador de navegación para redirigir al usuario a otras pantallas.
 * @param email El correo electrónico del usuario, utilizado para crear la ruta a la pantalla de inscripción.
 */
@Composable
fun CourseListItem(
    course: Course,
    navController: NavController,
    email: String?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = course.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = course.instructor,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = course.duration,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        Text(
                            text = " • ",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFFFFB800)
                        )
                        Text(
                            text = "${course.rating}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
                Text(
                    text = "$${course.price}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4285F4)
                )
            }
            Button(
                onClick = {
                    navController.navigate(Screen.CourseEnrollment.createRoute(course.id, email ?: ""))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4285F4)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Inscribirse", color = Color.White)
            }
        }
    }
}

/**
 * Lista de cursos de ejemplo con detalles como el título, el instructor, la duración, el precio, la calificación, etc.
 */
val coursesList = listOf(
    Course(
        id = "1",
        title = "Desarrollo Web Full Stack",
        instructor = "Carlos Guerra",
        duration = "60 horas",
        price = 49.99,
        rating = 4.7f,
        imageUrl = "",
        progress = 0,
        isFavorite = false
    ),
    Course(
        id = "2",
        title = "Python para Ciencia de Datos",
        instructor = "Alejandro Guerra",
        duration = "80 horas",
        price = 59.99,
        rating = 4.9f,
        imageUrl = "",
        progress = 0,
        isFavorite = false
    ),
    Course(
        id = "3",
        title = "DevOps y CI/CD",
        instructor = "Cecilia Vivanco",
        duration = "40 horas",
        price = 79.99,
        rating = 4.9f,
        imageUrl = "",
        progress = 0,
        isFavorite = false
    )
)