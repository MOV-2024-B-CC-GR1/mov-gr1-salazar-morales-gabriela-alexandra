// ui/navigation/NavGraph.kt
package com.example.ccgr12024b_gasm.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ccgr12024b_gasm.ui.screens.auth.LoginScreen
import com.example.ccgr12024b_gasm.ui.screens.auth.RegisterScreen
import com.example.ccgr12024b_gasm.ui.screens.auth.RegistrationSuccessScreen
import com.example.ccgr12024b_gasm.ui.screens.home.HomeScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.ccgr12024b_gasm.ui.screens.courses.CoursesScreen
import com.example.ccgr12024b_gasm.ui.screens.courses.detail.CourseDetailScreen
import com.example.ccgr12024b_gasm.ui.screens.courses.enrollment.CourseEnrollmentScreen
import com.example.ccgr12024b_gasm.ui.screens.progress.ProgressScreen
import com.example.ccgr12024b_gasm.ui.screens.profile.ProfileScreen
import com.example.ccgr12024b_gasm.ui.screens.profile.edit.EditProfileScreen
import com.example.ccgr12024b_gasm.ui.screens.profile.password.ChangePasswordScreen
import com.example.ccgr12024b_gasm.ui.screens.profile.password.PasswordUpdatedScreen

/**
 * Definición del gráfico de navegación de la aplicación, que establece las rutas y los destinos para la navegación entre pantallas.
 * La función utiliza Jetpack Compose y la librería de navegación para gestionar el flujo de pantallas de la aplicación.
 *
 * @param navController El controlador de navegación que maneja la navegación entre las pantallas.
 */
@Composable
fun TechAcademyNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Ruta y pantalla de inicio de sesión
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        // Ruta y pantalla de registro
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
        // Ruta y pantalla de éxito de registro
        composable(Screen.RegistrationSuccess.route) {
            RegistrationSuccessScreen(navController = navController)
        }
        // Ruta para la pantalla de inicio, que recibe un parámetro 'email'
        composable(
            route = "home/{email}",  // Ruta con parámetro
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            HomeScreen(
                navController = navController,
                email = email
            )
        }
        // Ruta para la pantalla de cursos, que recibe un parámetro 'email'
        composable(
            route = "courses/{email}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            CoursesScreen(
                navController = navController,
                email = email
            )
        }
        // Ruta para la pantalla de progreso, recibe un parámetro 'email'
        composable(
            route = "progress/{email}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            ProgressScreen(
                navController = navController,
                email = email
            )
        }
        // Ruta para la pantalla de perfil, recibe el parámetro 'email'
        composable(
            route = "profile/{email}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            ProfileScreen(
                navController = navController,
                email = email
            )
        }
        // Ruta para la pantalla de detalles del curso, recibe los parámetros 'courseId' y 'email'
        composable(
            route = "course_detail/{courseId}/{email}",
            arguments = listOf(
                navArgument("courseId") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
            val email = backStackEntry.arguments?.getString("email")
            CourseDetailScreen(
                navController = navController,
                courseId = courseId,
                email = email
            )
        }
        // Ruta para la pantalla de inscripción al curso, recibe los parámetros 'courseId' y 'email'
        composable(
            route = "course_enrollment/{courseId}/{email}",
            arguments = listOf(
                navArgument("courseId") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
            val email = backStackEntry.arguments?.getString("email")
            CourseEnrollmentScreen(
                navController = navController,
                courseId = courseId,
                email = email
            )
        }
        // Ruta para la pantalla de edición de perfil, recibe el parámetro 'email'
        composable(
            route = "edit_profile/{email}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            EditProfileScreen(
                navController = navController,
                email = email
            )
        }

        // Ruta para la pantalla de cambio de contraseña
        composable(
            route = "change_password/{email}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            ChangePasswordScreen(
                navController = navController,
                email = email
            )
        }

        // Ruta para la pantalla de confirmación de cambio de contraseña
        composable(route = Screen.PasswordUpdated.route) {
            PasswordUpdatedScreen(
                navController = navController
            )
        }
    }
}