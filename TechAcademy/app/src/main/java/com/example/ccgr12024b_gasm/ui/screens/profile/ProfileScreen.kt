package com.example.ccgr12024b_gasm.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ccgr12024b_gasm.data.database.DatabaseHelper
import com.example.ccgr12024b_gasm.ui.navigation.Screen
import com.example.ccgr12024b_gasm.ui.screens.profile.components.ProfileHeader
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.ccgr12024b_gasm.R

/**
 * Representa un ítem de navegación dentro de la barra inferior
 */
data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val selected: Boolean
)

/**
 * Composable que representa la pantalla de perfil de usuario.
 * @param navController Controlador de navegación.
 * @param email Correo electrónico del usuario autenticado.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    email: String? = null
) {
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }
    val user = remember(email) { email?.let { dbHelper.getUserByEmail(it) } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            //painter = painterResource(id = R.drawable.tech_academy_logo),
                            painter = painterResource(id = R.drawable.buho),
                            contentDescription = "Logo",
                            modifier = Modifier.size(30.dp)
                        )
                        Text(
                            text = "Tech Academy",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                val navigationItems = listOf(
                    NavigationItem("Inicio", Icons.Default.Home, false),
                    NavigationItem("Cursos", Icons.Default.MenuBook, false),
                    NavigationItem("Progreso", Icons.Default.ShowChart, false),
                    NavigationItem("Perfil", Icons.Default.Person, true)
                )

                navigationItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = item.selected,
                        onClick = {
                            when (item.title) {
                                "Inicio" -> navController.navigate(Screen.Home.createRoute(email ?: ""))
                                "Cursos" -> navController.navigate(Screen.Courses.createRoute(email ?: ""))
                                "Progreso" -> navController.navigate(Screen.Progress.createRoute(email ?: ""))
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF4285F4),
                            unselectedIconColor = Color.Gray,
                            selectedTextColor = Color(0xFF4285F4),
                            unselectedTextColor = Color.Gray,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8F9FA))
        ) {
            // Sección de perfil
            ProfileHeader(
                name = user?.name ?: "",
                email = email ?: "",
                initials = user?.name?.take(2)?.uppercase() ?: ""
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Menú de opciones
            MenuOption(
                title = "Editar Perfil",
                icon = Icons.Default.Person,
                onClick = {
                    navController.navigate(Screen.EditProfile.createRoute(email ?: ""))
                }
            )

            MenuOption(
                title = "Notificaciones",
                icon = Icons.Default.Notifications,
                onClick = { }
            )

            MenuOption(
                title = "Certificados",
                icon = Icons.Default.EmojiEvents,
                onClick = { }
            )

            MenuOption(
                title = "Cursos Guardados",
                icon = Icons.Default.BookmarkBorder,
                onClick = { }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón Cerrar Sesión
            Button(
                onClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFFFFF)
            ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Cerrar Sesión",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

/**
 * Encabezado de perfil que muestra el nombre, email e iniciales del usuario.
 */
@Composable
private fun ProfileHeader(
    name: String,
    email: String,
    initials: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFF4285F4)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = email,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

/**
 * Opción del menú en la pantalla de perfil.
 */
@Composable
private fun MenuOption(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
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
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF8E8E93),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFFC7C7CC)
            )
        }
    }
}