package com.example.ccgr12024b_gasm.ui.screens.courses

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ccgr12024b_gasm.R
import com.example.ccgr12024b_gasm.data.model.Course
import com.example.ccgr12024b_gasm.ui.navigation.Screen
import com.example.ccgr12024b_gasm.ui.screens.courses.components.CategoryCard
import com.example.ccgr12024b_gasm.ui.screens.courses.components.CourseListItem
import com.example.ccgr12024b_gasm.ui.screens.courses.models.Category

/**
 * Modelo para los elementos de navegación de la barra inferior.
 *
 * @param title Título del elemento de navegación.
 * @param icon Ícono asociado al elemento de navegación.
 * @param selected Indica si el elemento de navegación está seleccionado.
 */
data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val selected: Boolean
)

/**
 * Pantalla que muestra una lista de cursos disponibles, junto con un campo de búsqueda, categorías de cursos,
 * y un sistema de pestañas para filtrar los cursos por estado (Todos, En Progreso, Completados).
 * Los usuarios pueden ver los detalles de cada curso, acceder a información sobre las categorías y realizar una búsqueda.
 *
 * @param navController Controlador de navegación utilizado para navegar entre pantallas.
 * @param email El correo electrónico del usuario que se pasa a otras pantallas, utilizado para personalizar las rutas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(
    navController: NavController,
    email: String? = null
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Todos", "En Progreso", "Completados")

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
                    NavigationItem("Cursos", Icons.Default.MenuBook, true),
                    NavigationItem("Progreso", Icons.Default.ShowChart, false),
                    NavigationItem("Perfil", Icons.Default.Person, false)
                )

                navigationItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, item.title) },
                        label = { Text(item.title) },
                        selected = item.selected,
                        onClick = {
                            when (item.title) {
                                "Inicio" -> navController.navigate(Screen.Home.createRoute(email ?: ""))
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
            var filteredCourses by remember { mutableStateOf(coursesList) }
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    filteredCourses = coursesList.filter {
                        it.title.contains(query, ignoreCase = true) ||
                                it.instructor.contains(query, ignoreCase = true)
                    }
                },
                placeholder = { Text("Buscar cursos...") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Buscar"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = Color(0xFF4285F4)
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            Text(
                text = "Categorías",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            LazyRow(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { category ->
                    CategoryCard(category)
                }
            }

            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White,
                contentColor = Color(0xFF4285F4)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredCourses) { course ->
                    CourseListItem(
                        course = course,
                        navController = navController,
                        email = email
                    )
                }
            }
        }
    }
}
// Lista de categorías disponibles para los cursos
val categories = listOf(
    Category("1", "Programación", R.drawable.ic_programming),
    Category("2", "Ciencia de datos", R.drawable.ic_data_science),
    Category("3", "Cloud Computing", R.drawable.ic_cloud),
    Category("4", "Desarrollo Web", R.drawable.ic_web)
)
// Lista de cursos disponibles para mostrar en la pantalla
val coursesList = listOf(
    Course(
        id = "1",
        title = "Desarrollo Web Full Stack",
        instructor = "Carlos Guerra",
        duration = "60 horas",
        price = 49.99,
        rating = 4.7f,
        imageUrl = "",
        progress = 0,
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
    ),
    Course(
        id = "3",
        title = "DevOps y CI/CD",
        instructor = "Cecilia Vivanco",
        duration = "40 horas",
        price = 79.99,
        rating = 4.9f,
        imageUrl = "",
        progress = 0,
        isFavorite = false
    )
)