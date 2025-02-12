// ModuleCard.kt
package com.example.ccgr12024b_gasm.ui.screens.courses.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ccgr12024b_gasm.ui.screens.courses.detail.CourseModule

/**
 * Componente que muestra una tarjeta con la información de un módulo de curso. La tarjeta muestra el título,
 * descripción y un ícono representativo del módulo. Cuando se hace clic en la tarjeta, esta se expande
 * para mostrar una lista de lecciones asociadas al módulo. Además, el color de la tarjeta cambia cuando está expandida.
 *
 * @param module El objeto de tipo [CourseModule] que contiene los datos del módulo, como el título, descripción
 *               y las lecciones.
 * @param isExpanded Un valor booleano que indica si el módulo está expandido o no. Esto afecta la apariencia de la tarjeta.
 * @param onExpandClick Función que se ejecuta cuando el usuario hace clic en la tarjeta para expandirla o contraerla.
 */
@Composable
fun ModuleCard(
    module: CourseModule,
    isExpanded: Boolean,
    onExpandClick: () -> Unit
) {
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onExpandClick),
            colors = CardDefaults.cardColors(
                containerColor = if (isExpanded) Color(0xFFF1F6FF) else Color.White
            )
        ) {
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
        }

        if (isExpanded && module.lessons.isNotEmpty()) {
            module.lessons.forEach { lesson ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(20.dp)
                    )
                    Column(
                        modifier = Modifier.padding(start = 12.dp)
                    ) {
                        Text(
                            text = lesson.title,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "${lesson.durationInHours} horas",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
