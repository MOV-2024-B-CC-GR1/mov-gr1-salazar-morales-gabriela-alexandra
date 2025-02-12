package com.example.ccgr12024b_gasm.ui.navigation

/**
 * Clase sellada (sealed class) que representa las diferentes pantallas en la aplicación. Cada objeto
 * dentro de la clase define una ruta única para una pantalla específica.
 *
 * La clase `Screen` es usada para gestionar de manera centralizada las rutas de la navegación y asegurar
 * que la navegación sea coherente en toda la aplicación.
 *
 * @property route La ruta asociada a una pantalla. Cada pantalla tiene una ruta única que se usa para la navegación.
 */
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object RegistrationSuccess : Screen("registration_success")
    object Home : Screen("home/{email}")
    object Courses : Screen("courses/{email}")
    object Progress : Screen("progress/{email}")
    object Profile : Screen("profile/{email}")
    object CourseDetail : Screen("course_detail/{courseId}/{email}") {
        fun createRoute(courseId: String, email: String) = "course_detail/$courseId/$email"
    }
    object CourseEnrollment : Screen("course_enrollment/{courseId}/{email}") {
        fun createRoute(courseId: String, email: String) = "course_enrollment/$courseId/$email"
    }

    object EditProfile : Screen("edit_profile/{email}") {
        override fun createRoute(email: String) = "edit_profile/$email"
    }

    object ChangePassword : Screen("change_password/{email}") {
        override fun createRoute(email: String) = "change_password/$email"
    }
    object PasswordUpdated : Screen("password_updated")

    /**
     * Función para crear la ruta para diferentes pantallas de la aplicación.
     * Cada pantalla puede tener parámetros dinámicos, como el 'email' del usuario.
     *
     * @param email El correo electrónico del usuario, utilizado para generar la ruta.
     * @return La ruta correspondiente para la pantalla con el parámetro 'email'.
     */
    open fun createRoute(email: String) = when (this) {
        is Home -> "home/$email"
        is Courses -> "courses/$email"
        is Progress -> "progress/$email"
        is Profile -> "profile/$email"
        else -> route
    }
}