package com.example.ccgr12024b_gasm.ui.screens.progress.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ccgr12024b_gasm.R
import com.example.ccgr12024b_gasm.data.model.Course
import com.example.ccgr12024b_gasm.ui.navigation.Screen
import androidx.navigation.NavController

/**
 * Composable que muestra una tarjeta de progreso de un curso.
 *
 * @param course Información del curso.
 * @param progress Progreso del usuario en el curso (valor entre 0 y 1).
 * @param currentLessons Número de lecciones actuales completadas o disponibles.
 * @param navController Controlador de navegación para redirigir a la pantalla de detalles del curso.
 * @param email Correo electrónico del usuario, utilizado en la navegación.
 */
@Composable
fun CourseProgressCard(
    course: Course,
    progress: Float,
    currentLessons: String,
    navController: NavController,
    email: String?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    if (course.title.contains("Python")) R.drawable.python_course
                    else R.drawable.web_course
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = course.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$currentLessons lecciones",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .height(4.dp),
                    color = Color(0xFF4285F4),
                    trackColor = Color(0xFFE0E0E0)
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Button(
                onClick = {
                    navController.navigate(Screen.CourseDetail.createRoute(course.id, email ?: ""))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4285F4)
                ),
                shape = RoundedCornerShape(20.dp),  // Forma más redondeada
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 12.dp,
                    top = 8.dp,
                    bottom = 8.dp
                ),
                modifier = Modifier.height(36.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Continuar",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    Icon(
                        imageVector = Icons.Default.PlayArrow,  // Cambiado a PlayArrow
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}