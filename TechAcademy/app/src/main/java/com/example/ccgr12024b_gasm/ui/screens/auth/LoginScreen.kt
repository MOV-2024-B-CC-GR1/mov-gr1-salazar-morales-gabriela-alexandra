package com.example.ccgr12024b_gasm.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ccgr12024b_gasm.R
import com.example.ccgr12024b_gasm.data.database.DatabaseHelper
import com.example.ccgr12024b_gasm.ui.navigation.Screen

/**
 * Pantalla de inicio de sesión donde el usuario puede ingresar su correo electrónico y contraseña
 * para acceder a la aplicación. Valida los campos de entrada y realiza el proceso de autenticación.
 *
 * @param navController Controlador de navegación que maneja el flujo de pantallas en la aplicación.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Image(
            //painter = painterResource(id = R.drawable.tech_academy_logo),
            painter = painterResource(id = R.drawable.buho),
            contentDescription = "Tech Academy Logo",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 24.dp)
        )

        Text(
            text = "Tech Academy",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = null
            },
            label = { Text("Correo electrónico") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = if (errorMessage != null) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                errorBorderColor = MaterialTheme.colorScheme.error
            ),
            isError = errorMessage != null
        )

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = null
            },
            label = { Text("Contraseña") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password",
                    tint = if (errorMessage != null) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff
                        else Icons.Default.Visibility,
                        contentDescription = if (passwordVisible) "Ocultar contraseña"
                        else "Mostrar contraseña",
                        tint = if (errorMessage != null) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.primary
                    )
                }
            },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (errorMessage == null) 24.dp else 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                errorBorderColor = MaterialTheme.colorScheme.error
            ),
            isError = errorMessage != null
        )

        // Error Message
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Login Button
        // Login Button
        Button(
            onClick = {
                // Verificamos la estructura de la base de datos
                dbHelper.verifyDatabaseStructure()
                // Mostramos la lista de usuarios
                dbHelper.debugListUsers()

                println("DEBUG: Intentando login con -> Email: $email, Password: $password")

                when {
                    email.isBlank() || password.isBlank() -> {
                        errorMessage = "Por favor completa todos los campos"
                        println("DEBUG: Campos vacíos detectados")
                    }
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        errorMessage = "Correo electrónico inválido"
                        println("DEBUG: Email inválido detectado")
                    }
                    else -> {
                        println("DEBUG: Validando credenciales...")
                        if (dbHelper.loginUser(email, password)) {
                            println("DEBUG: Login exitoso")
                            navController.navigate("home/$email") {  // Pasamos el email como parámetro
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        } else {
                            println("DEBUG: Login fallido - Credenciales incorrectas")
                            errorMessage = "Credenciales incorrectas"
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Iniciar Sesión", color = Color.White)
        }

        // Forgot Password
        TextButton(
            onClick = { /* TODO: Implement forgot password */ },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                "¿Olvidaste tu contraseña?",
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Register Link
        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("¿No tienes una cuenta?")
            TextButton(onClick = { navController.navigate("register") }) {
                Text(
                    "Regístrate",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}