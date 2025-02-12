package com.example.ccgr12024b_gasm.ui.screens.courses.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

/**
 * Componente que muestra un par de texto: un valor y una etiqueta.
 * Este componente es útil para mostrar estadísticas o métricas relacionadas con un curso, como duración, calificación, etc.
 *
 * @param label El texto que describe la métrica o estadística (por ejemplo, "Duración", "Calificación").
 * @param value El valor de la métrica o estadística (por ejemplo, "60 horas", "4.5 estrellas").
 */

@Composable
fun CourseStatItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}