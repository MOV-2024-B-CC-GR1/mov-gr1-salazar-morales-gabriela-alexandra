package com.example.ccgr12024b_gasm.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ccgr12024b_gasm.R
import com.example.ccgr12024b_gasm.ui.navigation.Screen
import androidx.compose.ui.platform.LocalContext
import com.example.ccgr12024b_gasm.data.database.DatabaseHelper

/**
 * Pantalla de registro donde el usuario puede ingresar su nombre, correo electrónico, número de teléfono,
 * contraseña y confirmar la contraseña. Realiza validaciones en los campos y registra al usuario en la base de datos.
 *
 * @param navController Controlador de navegación que maneja el flujo de pantallas en la aplicación.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // Inicializar dbHelper y mensaje de error general
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()

    // Funciones de validación
    val validateName = { input: String ->
        nameError = when {
            input.isBlank() -> "El nombre es requerido"
            input.length < 3 -> "El nombre debe tener al menos 3 caracteres"
            else -> null
        }
    }

    val validateEmail = { input: String ->
        emailError = when {
            input.isBlank() -> "El correo electrónico es requerido"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches() ->
                "Correo electrónico inválido"
            else -> null
        }
    }

    val validatePhone = { input: String ->
        phoneError = when {
            input.isBlank() -> "El número de teléfono es requerido"
            !input.all { it.isDigit() } -> "El teléfono debe contener solo números"
            input.length < 10 -> "El teléfono debe tener al menos 10 dígitos"
            else -> null
        }
    }

    val validatePassword = { input: String ->
        passwordError = when {
            input.length < 8 -> "La contraseña debe tener al menos 8 caracteres"
            !input.any { it.isUpperCase() } -> "Debe contener al menos una mayúscula"
            !input.any { it.isLowerCase() } -> "Debe contener al menos una minúscula"
            !input.any { it.isDigit() } -> "Debe contener al menos un número"
            !input.any { !it.isLetterOrDigit() } -> "Debe contener al menos un carácter especial"
            else -> null
        }
        if (confirmPassword.isNotEmpty()) {
            confirmPasswordError = when {
                confirmPassword != input -> "Las contraseñas no coinciden"
                else -> null
            }
        }
    }

    val validateConfirmPassword = { input: String ->
        confirmPasswordError = when {
            input.isBlank() -> "Debe confirmar la contraseña"
            input != password -> "Las contraseñas no coinciden"
            else -> null
        }
    }

    // Diseño de la pantalla utilizando un Column para organizar los elementos.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo de la aplicación
        Image(
            //painter = painterResource(id = R.drawable.tech_academy_logo),
            painter = painterResource(id = R.drawable.buho),
            contentDescription = "Tech Academy Logo",
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 16.dp)
        )
        // Título de la pantalla
        Text(
            text = "Tech Academy",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        // Campo de entrada para el nombre completo
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                validateName(it)
            },
            label = { Text("Nombre Completo") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Name",
                    tint = if (nameError != null) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary
                )
            },
            isError = nameError != null,
            supportingText = {
                if (nameError != null) {
                    Text(
                        text = nameError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de entrada para el correo electrónico
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                validateEmail(it)
            },
            label = { Text("Correo electrónico") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = if (emailError != null) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary
                )
            },
            isError = emailError != null,
            supportingText = {
                if (emailError != null) {
                    Text(
                        text = emailError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de entrada para el número de teléfono
        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it
                validatePhone(it)
            },
            label = { Text("Número de teléfono") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Phone",
                    tint = if (phoneError != null) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary
                )
            },
            isError = phoneError != null,
            supportingText = {
                if (phoneError != null) {
                    Text(
                        text = phoneError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de entrada para la contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                validatePassword(it)
            },
            label = { Text("Contraseña") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password",
                    tint = if (passwordError != null) MaterialTheme.colorScheme.error
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
                        tint = if (passwordError != null) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.primary
                    )
                }
            },
            isError = passwordError != null,
            supportingText = {
                if (passwordError != null) {
                    Text(
                        text = passwordError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de entrada para confirmar la contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                validateConfirmPassword(it)
            },
            label = { Text("Confirmar contraseña") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Confirm Password",
                    tint = if (confirmPasswordError != null) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = if (confirmPasswordVisible) Icons.Default.VisibilityOff
                        else Icons.Default.Visibility,
                        contentDescription = if (confirmPasswordVisible) "Ocultar contraseña"
                        else "Mostrar contraseña",
                        tint = if (confirmPasswordError != null) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.primary
                    )
                }
            },
            isError = confirmPasswordError != null,
            supportingText = {
                if (confirmPasswordError != null) {
                    Text(
                        text = confirmPasswordError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Mensaje de error general
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        // Botón de registro
        Button(
            onClick = {
                // Realizar todas las validaciones
                validateName(name)
                validateEmail(email)
                validatePhone(phone)
                validatePassword(password)
                validateConfirmPassword(confirmPassword)

                // Verificar si hay errores
                if (nameError == null && emailError == null &&
                    phoneError == null && passwordError == null &&
                    confirmPasswordError == null) {

                    println("DEBUG: Intentando registrar usuario -> Email: $email, Name: $name")

                    // Verificar estructura de la base de datos
                    dbHelper.verifyDatabaseStructure()

                    if (dbHelper.registerUser(email, name, phone, password)) {
                        println("DEBUG: Registro exitoso")
                        // Verificar que el usuario se guardó
                        dbHelper.debugListUsers()
                        navController.navigate(Screen.RegistrationSuccess.route)
                    } else {
                        println("DEBUG: Registro fallido")
                        errorMessage = "Error al registrar el usuario"
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Registrarse")
        }

        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("¿Ya tienes una cuenta?")
            TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
                Text(
                    text = "Inicia sesión",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}