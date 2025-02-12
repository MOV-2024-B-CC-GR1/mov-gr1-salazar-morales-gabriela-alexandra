package com.example.ccgr12024b_gasm.ui.screens.courses.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.ccgr12024b_gasm.data.model.Course
import com.example.ccgr12024b_gasm.ui.screens.courses.detail.components.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.ccgr12024b_gasm.ui.navigation.Screen


data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val selected: Boolean
)

data class Lesson(val title: String, val durationInHours: Int)

data class CourseModule(
    val id: String,
    val title: String,
    val description: String,
    val lessons: List<Lesson>
)

fun getCourseById(courseId: String): Course {
    return Course(
        id = courseId,
        title = if (courseId == "1") "Desarrollo Web Full Stack" else "Python para Ciencia de Datos",
        instructor = "Carlos Guerra",
        duration = "60 horas",
        price = 49.99,
        rating = 4.7f,
        imageUrl = "",
        progress = 80,
        isFavorite = false
    )
}

/**
 * Pantalla que muestra los detalles completos de un curso seleccionado.
 * Muestra información relevante sobre el curso como el título, el instructor, el progreso, y el contenido del curso.
 * Además, permite la navegación entre diferentes pantallas relacionadas con el curso.
 *
 * @param navController Controlador de navegación para manejar las rutas de la pantalla.
 * @param courseId El ID del curso que se muestra.
 * @param email El correo electrónico del usuario, usado para la navegación.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    navController: NavController,
    courseId: String,
    email: String? = null
) {
    val course = getCourseById(courseId)
    var expandedModule by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                val navigationItems = listOf(
                    NavigationItem("Inicio", Icons.Default.Home, false),
                    NavigationItem("Cursos", Icons.Default.MenuBook, false),
                    NavigationItem("Progreso", Icons.Default.ShowChart, false),
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
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF4285F4))
                    .padding(24.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Certificado Profesional",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    Text(
                        text = course.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Text(
                        text = "De Principiante a Desarrollador Profesional",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Row(
                        modifier = Modifier.padding(top = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = course.instructor,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    LinearProgressIndicator(
                        progress = course.progress / 100f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.3f)
                    )

                    Text(
                        text = "${course.progress}% completado",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CourseStatItem("Módulos", "2/3")
                            CourseStatItem("Tiempo", "60 horas")
                            CourseStatItem("Nivel", "Profesional")
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Contenido del curso",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )



                val modules = if (course.title.contains("Web")) {
                    listOf(
                        CourseModule(
                            id = "python_fundamentals",
                            title = "Fundamentos de Python",
                            description = "Base sólida en lógica de programación",
                            lessons = listOf(
                                Lesson("Introducción a la Programación", 3),
                                Lesson("Variables, Tipos de Datos y Operadores", 2),
                                Lesson("Estructuras de Control (Condicionales y Bucles)", 3),
                                Lesson("Funciones y Modularidad", 2),
                                Lesson("Manejo de Errores", 2),
                                Lesson("Introducción a Algoritmos y Estructuras de Datos", 3),
                                Lesson("Bibliotecas de Python para Ciencia de Datos", 2),  // Agregada
                                Lesson("Procesamiento de Archivos y Datos en Python", 2),  // Agregada
                                Lesson("Introducción a Pandas y Numpy", 3),  // Agregada
                                Lesson("Visualización de Datos con Matplotlib", 3)  // Agregada
                            )
                        ),
                        CourseModule(
                            id = "backend",
                            title = "Backend Profesional",
                            description = "Arquitectura de servidores y APIs",
                            lessons = listOf(
                                Lesson("Fundamentos de Arquitectura de Servidores", 3),
                                Lesson("Introducción a APIs RESTful", 3),
                                Lesson("Autenticación y Autorización", 2),
                                Lesson("Bases de Datos NoSQL y SQL", 3),
                                Lesson("Diseño de API con Node.js", 4),
                                Lesson("Despliegue de Aplicaciones Backend", 3)
                            )
                        )
                    )
                } else {
                    listOf(
                        CourseModule(
                            id = "python",
                            title = "Fundamentos de Python",
                            description = "Base sólida en programación",
                            lessons = listOf(
                                Lesson("Tipos de datos y variables", 2),
                                Lesson("Control de flujo", 2),
                                Lesson("Funciones y módulos", 3),
                                Lesson("Manejo de errores", 2),
                                Lesson("Estructuras de datos básicas", 3),
                                Lesson("Introducción a la programación orientada a objetos", 3)
                            )
                        ),
                        CourseModule(
                            id = "data",
                            title = "Análisis de Datos",
                            description = "Procesamiento y visualización",
                            lessons = listOf(
                                Lesson("Introducción al análisis de datos", 3),
                                Lesson("Manejo de datos con Pandas", 3),
                                Lesson("Limpieza de datos", 2),
                                Lesson("Visualización con Matplotlib", 3),
                                Lesson("Análisis de datos con Numpy", 3),
                                Lesson("Estadísticas básicas para análisis de datos", 2),
                                Lesson("Modelos de regresión básicos", 3)
                            )
                        )
                    )
                }

                modules.forEach { module ->
                    ModuleCard(
                        module = module,
                        isExpanded = expandedModule == module.id,
                        onExpandClick = {
                            expandedModule = if (expandedModule == module.id) null else module.id
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}