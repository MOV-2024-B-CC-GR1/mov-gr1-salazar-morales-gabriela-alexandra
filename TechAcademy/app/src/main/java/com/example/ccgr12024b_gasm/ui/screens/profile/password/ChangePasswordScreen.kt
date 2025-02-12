package com.example.ccgr12024b_gasm.ui.screens.profile.password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ccgr12024b_gasm.R
import com.example.ccgr12024b_gasm.data.database.DatabaseHelper
import com.example.ccgr12024b_gasm.ui.navigation.Screen
import com.example.ccgr12024b_gasm.ui.screens.profile.password.components.PasswordTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    navController: NavController,
    email: String? = null
) {
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.tech_academy_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(60.dp)
                .padding(vertical = 16.dp)
        )

        Text(
            text = "Tech Academy",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Ingresa y confirma tu nueva contraseña",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        PasswordTextField(
            value = password,
            onValueChange = {
                password = it
                error = null
            },
            label = "Contraseña",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        PasswordTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                error = null
            },
            label = "Confirmar contraseña",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        if (error != null) {
            Text(
                text = error!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Button(
            onClick = {
                when {
                    password.isEmpty() || confirmPassword.isEmpty() -> {
                        error = "Por favor completa todos los campos"
                    }
                    password != confirmPassword -> {
                        error = "Las contraseñas no coinciden"
                    }
                    password.length < 6 -> {
                        error = "La contraseña debe tener al menos 6 caracteres"
                    }
                    else -> {
                        // Actualizar contraseña en la base de datos
                        email?.let { safeEmail ->
                            if (dbHelper.updatePassword(safeEmail, password)) {
                                // Si la actualización fue exitosa
                                navController.navigate(Screen.PasswordUpdated.route) {
                                    // Limpiar el back stack para que no pueda volver atrás
                                    popUpTo(Screen.PasswordUpdated.route) { inclusive = true }
                                }
                            } else {
                                error = "Error al actualizar la contraseña"
                            }
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF673AB7)
            )
        ) {
            Text(
                "Cambiar contraseña",
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        if (error != null) {
            Text(
                text = error!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}