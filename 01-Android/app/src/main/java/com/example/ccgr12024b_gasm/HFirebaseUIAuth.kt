package com.example.ccgr12024b_gasm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult


class HFirebaseUIAuth : AppCompatActivity() {
    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hfirebase_uiauth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }*/

    private val respuestaLoginUiAuth = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res: FirebaseAuthUIAuthenticationResult ->
        if (res.resultCode == RESULT_OK) {
            if (res.idpResponse != null) {
                seLogeo(res.idpResponse!!)
            }
        }
    }

    fun seLogeo(res: IdpResponse) {
        val nombre = FirebaseAuth.getInstance().currentUser?.displayName
        cambiarInterfaz(View.INVISIBLE, View.VISIBLE, "Bienvenido de nuevo")
        if (res.isNewUser == true) {
            registrarUsuarioPorPrimeraVez(res)
        }
    }

    /*
    fun cambiarInterfaz() {
        val visibilidadLogin: Int = View.VISIBLE
        val visibilidadLogout: Int = View.INVISIBLE
        val textoTextView: String = "Bienvenido"

        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        val tvBienvenida = findViewById<TextView>(R.id.tv_bienvenido)

        btnLogin.visibility = visibilidadLogin
        btnLogout.visibility = visibilidadLogout
        tvBienvenida.text = textoTextView
    }

    */

    fun cambiarInterfaz(visibilidadLogin: Int, visibilidadLogout: Int, mensaje: String) {
        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        val tvBienvenida = findViewById<TextView>(R.id.tv_bienvenido)

        btnLogin.visibility = visibilidadLogin
        btnLogout.visibility = visibilidadLogout
        tvBienvenida.text = mensaje
    }

    // Registramos en nuestro sistema y enviamos correo, etc.
    fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse) {
        // Implementación pendiente
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hfirebase_uiauth)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { _, insets ->
            // Implementación para manejar insets si es necesario
            insets
        }

        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )

            val logearseIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()

            respuestaLoginUiAuth.launch(logearseIntent)
        }

        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        btnLogout.setOnClickListener {
            cambiarInterfaz(View.INVISIBLE, View.VISIBLE, "Bienvenido de nuevo")
            FirebaseAuth.getInstance().signOut()
        }

        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario != null) {
            cambiarInterfaz(View.INVISIBLE, View.VISIBLE, usuario.displayName!!)
        }
    }

}