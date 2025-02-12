package com.example.ccgr12024b_gasm.ui.screens.profile.edit

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ccgr12024b_gasm.R
import com.example.ccgr12024b_gasm.data.database.DatabaseHelper
import com.example.ccgr12024b_gasm.ui.screens.profile.edit.components.EditProfileField
import com.example.ccgr12024b_gasm.ui.navigation.Screen

/**
 * Pantalla de edición de perfil.
 * Permite modificar datos como el nombre, teléfono y ubicación del usuario.
 * Se conecta con la base de datos para obtener y actualizar la información del usuario.
 *
 * @param navController Controlador de navegación para gestionar las transiciones entre pantallas.
 * @param email Correo electrónico del usuario que se está editando.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    email: String? = null
) {
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }
    val user = remember(email) { email?.let { dbHelper.getUserByEmail(it) } }

    var name by remember { mutableStateOf(user?.name ?: "") }
    var phone by remember { mutableStateOf("+593 96 885 8906") }
    var location by remember { mutableStateOf("Quito, Ecuador") }
    var hasChanges by remember { mutableStateOf(false) }

    fun saveChanges() {
        user?.let {
            it.name = name
            it.phone = phone
            dbHelper.updateUser(it)
            Toast.makeText(context, "Cambios guardados", Toast.LENGTH_SHORT).show()
            navController.navigateUp()
        }
    }

    BackHandler(enabled = hasChanges) {
        // Mostrar diálogo de confirmación si hay cambios sin guardar
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                actions = {
                    if (hasChanges) {
                        TextButton(onClick = { saveChanges() }) {
                            Text("Guardar", color = Color(0xFF4285F4))
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            // Avatar y encabezado
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.edit_profile_icon),
                    contentDescription = "Edit Profile",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            }

            // Campos editables con estado
            EditProfileField(
                value = name,
                icon = Icons.Default.Person,
                label = "Nombre completo",
                onValueChange = {
                    name = it
                    hasChanges = true
                }
            )

            EditProfileField(
                value = email ?: "",
                icon = Icons.Default.Email,
                label = "Correo electrónico",
                enabled = false,
                onValueChange = { }
            )

            EditProfileField(
                value = phone,
                icon = Icons.Default.Phone,
                label = "Teléfono",
                onValueChange = {
                    phone = it
                    hasChanges = true
                }
            )

            EditProfileField(
                value = location,
                icon = Icons.Default.LocationOn,
                label = "Ubicación",
                onValueChange = {
                    location = it
                    hasChanges = true
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón Guardar
            Button(
                onClick = {
                    user?.let {
                        it.name = name
                        // Actualizar en la base de datos
                        dbHelper.updateUser(it)
                        // Mostrar mensaje de éxito
                        Toast.makeText(context, "Cambios guardados", Toast.LENGTH_SHORT).show()
                        // Regresar
                        navController.navigateUp()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4285F4)
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    "Guardar cambios",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Botón Cambiar contraseña
            Button(
                onClick = {
                    navController.navigate(Screen.ChangePassword.createRoute(email ?: ""))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4285F4)
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Cambiar contraseña",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}