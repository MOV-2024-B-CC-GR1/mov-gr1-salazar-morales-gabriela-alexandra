package com.example.ccgr12024b_gasm.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ccgr12024b_gasm.R
import com.example.ccgr12024b_gasm.data.model.Course
import com.example.ccgr12024b_gasm.data.database.DatabaseHelper
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.draw.clip
import com.example.ccgr12024b_gasm.ui.navigation.Screen
import androidx.compose.foundation.clickable

/**
 * Pantalla principal de la aplicación, donde se muestra un saludo al usuario,
 * una barra de navegación y una lista de cursos disponibles.
 * @param navController Controlador de navegación para moverse entre pantallas.
 * @param email Email del usuario autenticado.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    email: String? = null
) {
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }
    val user = remember(email) { email?.let { dbHelper.getUserByEmail(it) } }
    val userName = remember(user) { user?.name?.split(" ")?.first() ?: "Error" }

    Scaffold(
        topBar = {
            /**
             * Barra superior con el logo de la aplicación y un botón de notificaciones.
             */
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp)
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
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, "Notificaciones")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            /**
             * Barra de navegación inferior con acceso a diferentes secciones.
             */
            NavigationBar(
                containerColor = Color.White
            ) {
                listOf(
                    NavigationItem("Inicio", Icons.Default.Home, true),
                    NavigationItem("Cursos", Icons.Default.MenuBook, false),
                    NavigationItem("Progreso", Icons.Default.ShowChart, false),
                    NavigationItem("Perfil", Icons.Default.Person, false)
                ).forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, item.title) },
                        label = { Text(item.title) },
                        selected = item.selected,
                        onClick = {
                            when (item.title) {
                                "Cursos" -> navController.navigate(Screen.Courses.createRoute(email ?: ""))
                                "Progreso" -> navController.navigate(Screen.Progress.createRoute(email ?: ""))
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
                .padding(16.dp)
        ) {
            Text(
                text = "¡Hola, $userName!",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "¡Continúa aprendiendo! 📚",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Text(
                text = "Cada día es una oportunidad para crecer. 🌱",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF4285F4)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Desarrollo Web Full Stack",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LinearProgressIndicator(
                        progress = 0.6f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.3f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            ),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = "Continuar",
                                    tint = Color(0xFF4285F4)
                                )
                                Text(
                                    text = "Continuar",
                                    color = Color(0xFF4285F4),
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                        }

                        Text(
                            text = "60% completado",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Cursos populares",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sampleCourses.size) { index ->
                    CourseCard(
                        course = sampleCourses[index],
                        navController = navController,  // Pasar el navController
                        email = email                   // Pasar el email
                    )
                }
            }
        }
    }
}

/**
 * Componente que representa la tarjeta de un curso en la lista de cursos populares.
 * @param course Curso que se mostrará en la tarjeta.
 * @param navController Controlador de navegación.
 * @param email Email del usuario autenticado.
 */
@Composable
fun CourseCard(
    course: Course,
    navController: NavController,  // Agregar NavController
    email: String?                 // Agregar email
) {
    var isFavorite by remember { mutableStateOf(course.isFavorite) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Navegar a la pantalla de inscripción al hacer clic en la tarjeta
                navController.navigate(Screen.CourseEnrollment.createRoute(course.id, email ?: ""))
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = painterResource(
                    if (course.title.contains("Python")) R.drawable.python_course
                    else R.drawable.web_course
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = course.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = course.instructor,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Icon(
                        Icons.Default.Timer,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Text(
                        text = course.duration,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFFFB800)
                    )
                    Text(
                        text = course.rating.toString(),
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$${course.price}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4285F4)
                )
                IconButton(onClick = { isFavorite = !isFavorite }) {
                    Icon(
                        if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorito",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        }
    }
}

val sampleCourses = listOf(
    Course(
        id = "1",
        title = "Desarrollo Web Full Stack",
        instructor = "Carlos Guerra",
        duration = "60 horas",
        price = 49.99,
        rating = 4.7f,
        imageUrl = "",
        progress = 60,
        isFavorite = false
    ),
    Course(
        id = "2",
        title = "Python para Ciencia de Datos",
        instructor = "Alejandro Guerra",
        duration = "80 horas",
        price = 59.99,
        rating = 4.9f,
        imageUrl = "",
        progress = 0,
        isFavorite = false
    )
)

/**
 * Clase que representa un elemento de la barra de navegación inferior.
 */
data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val selected: Boolean
)