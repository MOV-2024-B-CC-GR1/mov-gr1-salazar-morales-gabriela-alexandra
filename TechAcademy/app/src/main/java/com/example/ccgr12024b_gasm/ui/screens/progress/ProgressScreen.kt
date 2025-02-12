package com.example.ccgr12024b_gasm.ui.screens.progress

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ccgr12024b_gasm.R
import com.example.ccgr12024b_gasm.data.database.DatabaseHelper
import com.example.ccgr12024b_gasm.ui.screens.courses.components.coursesList
import com.example.ccgr12024b_gasm.ui.screens.progress.components.CertificateCard
import com.example.ccgr12024b_gasm.ui.screens.progress.components.CourseProgressCard
import com.example.ccgr12024b_gasm.ui.screens.progress.components.StatCard
import com.example.ccgr12024b_gasm.ui.navigation.Screen
import androidx.navigation.NavController

/**
 * Representa un ítem de navegación para la barra inferior.
 *
 * @param title El título del ítem de navegación.
 * @param icon El ícono del ítem de navegación.
 * @param selected Indica si el ítem está seleccionado.
 */
data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val selected: Boolean
)

/**
 * Pantalla de progreso que muestra estadísticas, cursos en progreso y certificados del usuario.
 *
 * Esta pantalla está diseñada para proporcionar una visión general del progreso del usuario en la plataforma,
 * incluyendo estadísticas de estudio, cursos en progreso y los certificados obtenidos.
 *
 * @param navController El controlador de navegación que permite navegar entre las pantallas.
 * @param email El correo electrónico del usuario, utilizado para obtener los datos del usuario desde la base de datos.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    navController: NavController,
    email: String? = null
) {
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }
    val user = remember(email) { email?.let { dbHelper.getUserByEmail(it) } }
    val userName = remember(user) { user?.name?.split(" ")?.first() ?: "Error" }

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
                    NavigationItem("Progreso", Icons.Default.ShowChart, true),
                    NavigationItem("Perfil", Icons.Default.Person, false)
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
                                "Perfil" -> navController.navigate(Screen.Profile.createRoute(email ?: ""))
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
                .padding(horizontal = 16.dp)
        ) {
            // Sección de ¡Buen trabajo!
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF1F6FF)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularUserAvatar(userName)
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "¡Buen trabajo!",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "✨",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        Text(
                            "Has completado 2 cursos este mes",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Sección de Estadísticas
            Text(
                "Estadísticas",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatCard("Horas de estudio", "24h", Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                StatCard("Cursos completados", "3", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatCard("En progreso", "2", Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                StatCard("Certificados", "3", Modifier.weight(1f))
            }

            // Sección de Cursos en progreso
            Text(
                "Cursos en progreso",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            coursesList
                .filter { it.title in listOf("Desarrollo Web Full Stack", "Python para Ciencia de Datos") }
                .forEach { course ->
                    CourseProgressCard(
                        course = course,
                        progress = if (course.title.contains("Web")) 8f/15f else 4f/10f,
                        currentLessons = if (course.title.contains("Web")) "8/15" else "4/10",
                        navController = navController,
                        email = email
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

            // Sección de Certificados
            Text(
                "Certificados",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            CertificateCard(
                title = "HTML & CSS Fundamentals",
                completionDate = "Completado el 15 de octubre"
            )
        }
    }
}

@Composable
fun CircularUserAvatar(name: String) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color(0xFF4285F4)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name.first().toString(),
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )
    }
}