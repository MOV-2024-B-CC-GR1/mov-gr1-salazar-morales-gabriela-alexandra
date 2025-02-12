package com.example.ccgr12024b_gasm.ui.screens.courses.enrollment.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ccgr12024b_gasm.data.model.CourseModule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Web
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayCircleOutline

/**
 * Componente que muestra una tarjeta (Card) con información sobre un módulo de un curso en el proceso de inscripción.
 * La tarjeta puede expandirse o contraerse al hacer clic, mostrando más detalles sobre las lecciones del módulo.
 *
 * @param module El módulo de curso a mostrar, que contiene un título, descripción y las lecciones asociadas.
 * @param isExpanded Estado que determina si la tarjeta está expandida o no. Si está expandida, muestra las lecciones.
 * @param onExpandClick Función que se ejecuta cuando el usuario hace clic para expandir o contraer la tarjeta.
 */
@Composable
fun EnrollmentModuleCard(
    module: CourseModule,
    isExpanded: Boolean,
    onExpandClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onExpandClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isExpanded) Color(0xFFF1F6FF) else Color.White
        )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = when {
                            module.id.contains("python") -> Icons.Default.Code
                            module.id.contains("frontend") -> Icons.Default.Web
                            module.id.contains("backend") -> Icons.Default.Storage
                            else -> Icons.Default.Code
                        },
                        contentDescription = null,
                        tint = Color(0xFF4285F4)
                    )
                    Column {
                        Text(
                            text = module.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = module.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Contraer" else "Expandir",
                    tint = Color(0xFF4285F4)
                )
            }

            if (isExpanded) {
                Column(
                    modifier = Modifier.padding(start = 56.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    module.lessons.forEach { lesson ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayCircleOutline,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                                Column(
                                    modifier = Modifier.padding(start = 12.dp)
                                ) {
                                    Text(
                                        text = lesson.title,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = "${lesson.duration} horas",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
