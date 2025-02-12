package com.example.ccgr12024b_gasm.ui.screens.courses.enrollment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ccgr12024b_gasm.data.model.Course
import com.example.ccgr12024b_gasm.data.model.getModulesForCourse
import com.example.ccgr12024b_gasm.ui.screens.courses.enrollment.components.*
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.*

/**
 * Pantalla que muestra la información de un curso seleccionado, permitiendo al usuario ver los detalles del curso,
 * los módulos, las lecciones y las opciones de inscripción. También tiene una barra de navegación inferior para
 * navegar entre las pantallas principales de la aplicación.
 *
 * @param navController El controlador de navegación para navegar entre pantallas.
 * @param courseId El ID del curso para obtener sus detalles y módulos.
 * @param email El correo electrónico del usuario, utilizado para personalizar algunas rutas y acciones.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseEnrollmentScreen(
    navController: NavController,
    courseId: String,
    email: String? = null
) {
    val course = getCourseById(courseId)
    var expandedModule by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Implementar favorito */ }) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorito")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "$${course.price}",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFF4285F4)
                        )
                        Text(
                            text = "$${59.99}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                textDecoration = TextDecoration.LineThrough
                            ),
                            color = Color.Gray
                        )
                    }
                    Button(
                        onClick = { /* Implementar inscripción */ },
                        modifier = Modifier.height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4285F4)
                        ),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = "Inscríbete",
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            // Header con fondo azul
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF4285F4))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = course.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                    Text(
                        text = "De Principiante a Desarrollador Profesional",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
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

                    Row(
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${course.rating}",
                                color = Color.White
                            )
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color.Yellow,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = "  •  5420 estudiantes",
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Text(
                                text = "Certificado Profesional",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF4285F4)
                            )
                        }
                    }

                    // Estadísticas
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatItem("Módulos", "3")
                            StatItem("Tiempo", "60 horas")
                            StatItem("Nivel", "Profesional")
                        }
                    }
                }
            }

            // Contenido del curso
            Text(
                text = "Contenido del curso",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )

            val modules = getModulesForCourse(courseId)
            modules.forEach { module ->
                EnrollmentModuleCard(
                    module = module,
                    isExpanded = expandedModule == module.id,
                    onExpandClick = {
                        expandedModule = if (expandedModule == module.id) null else module.id
                    }
                )
            }

            // Espacio adicional para el bottom bar
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

/**
 * Componente para mostrar las estadísticas de un curso (módulos, tiempo, nivel).
 *
 * @param label El texto de la estadística (por ejemplo, "Módulos").
 * @param value El valor de la estadística (por ejemplo, "3").
 */
@Composable
private fun StatItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}
private fun getCourseById(courseId: String): Course {
    return Course(
        id = courseId,
        title = if (courseId == "1") "Desarrollo Web Full Stack"
        else "Python para Ciencia de Datos",
        instructor = "Carlos Guerra",
        duration = "60 horas",
        price = 49.99,
        rating = 4.7f,
        imageUrl = "",
        progress = 0,
        isFavorite = false
    )
}