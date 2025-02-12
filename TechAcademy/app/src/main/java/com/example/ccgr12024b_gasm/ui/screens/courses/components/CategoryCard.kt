package com.example.ccgr12024b_gasm.ui.screens.courses.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ccgr12024b_gasm.ui.screens.courses.models.Category

/**
 * Pantalla que muestra un mensaje de éxito después de que un usuario haya completado el proceso de registro.
 * Informa al usuario que su cuenta ha sido creada correctamente y le ofrece la opción de iniciar sesión.
 *
 * @param navController Controlador de navegación que permite redirigir a otras pantallas (en este caso, la pantalla de Login).
 */
@Composable
fun CategoryCard(category: Category) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = category.iconRes),
                contentDescription = category.name,
                modifier = Modifier.size(32.dp),
                tint = Color(0xFF4285F4)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}