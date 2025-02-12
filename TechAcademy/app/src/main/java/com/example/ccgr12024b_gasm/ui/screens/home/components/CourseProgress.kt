package com.example.ccgr12024b_gasm.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ccgr12024b_gasm.data.model.Course

/**
 * Composable que muestra el progreso de un curso y un botón para continuar.
 *
 * @param course Objeto que representa el curso con información como título y progreso.
 * @param onContinueClick Función de callback que se ejecuta cuando se presiona el botón "Continuar".
 */
@Composable
fun CourseProgress(
    course: Course,
    onContinueClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Text(
                text = course.title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = course.progress / 100f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Color.White,
                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${course.progress}% completado",
                    color = Color.White
                )

                Button(
                    onClick = onContinueClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    )
                ) {
                    Text(
                        text = "Continuar",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Continuar curso",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

/**
 * Vista previa del componente CourseProgress con datos de ejemplo.
 */
@Preview(showBackground = true)
@Composable
fun CourseProgressPreview() {
    MaterialTheme {
        CourseProgress(
            course = Course(
                id = "1",
                title = "Desarrollo Web Full Stack",
                instructor = "Carlos Guerra",
                duration = "60 horas",
                price = 49.99,
                rating = 4.7f,
                imageUrl = "",
                progress = 60
            ),
            onContinueClick = {}
        )
    }
}