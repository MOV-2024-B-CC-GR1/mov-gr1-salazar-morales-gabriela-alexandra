package com.example.ccgr12024b_gasm.ui.screens.profile.password

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ccgr12024b_gasm.R
import com.example.ccgr12024b_gasm.ui.navigation.Screen

/**
 * Pantalla de edición de perfil.
 * Permite modificar datos como el nombre, teléfono y ubicación del usuario.
 * Se conecta con la base de datos para obtener y actualizar la información del usuario.
 *
 * @param navController Controlador de navegación para gestionar las transiciones entre pantallas.
 * @param email Correo electrónico del usuario que se está editando.
 */
@Composable
fun PasswordUpdatedScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_check_circle),
            contentDescription = null,
            tint = Color(0xFF4CAF50),
            modifier = Modifier.size(64.dp)
        )

        Text(
            text = "¡Contraseña actualizada!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Text(
            text = "Tu contraseña ha sido cambiada exitosamente.\nYa puedes usar tu nueva contraseña para iniciar sesión.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Button(
            onClick = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(0)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF673AB7)
            )
        ) {
            Text(
                "Iniciar sesión",
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}