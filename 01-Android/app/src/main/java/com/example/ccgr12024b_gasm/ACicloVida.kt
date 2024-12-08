package com.example.ccgr12024b_gasm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ACicloVida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_aciclo_vida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_ciclo_vida)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mostrarSnackbar("OnCreate")
    }

    override fun OnStart(){
        super.OnStart()
        mostrarSnackbar("OnStart")
    }

    override fun OnResume(){
        super.OnResume()
        mostrarSnackbar("OnResume")
    }

    override fun OnRestart(){
        super.OnRestart()
        mostrarSnackbar("OnRestart")
    }

    override fun OnPause(){
        super.OnPause()
        mostrarSnackbar("OnPause")
    }

    override fun OnStop(){
        super.OnStop()
        mostrarSnackbar("OnStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            //Guardar las variables
            putString("variableTextoGuardado", textoGlobal)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //Recuperar las variables
        val textoRecuperado: String? = savedInstanceState
            .getString("variableTextoGuardado")
        if(textoRecuperado != null){
            //textoGlobal = textoRecuperado
            mostrarSnackbar(textoRecuperado)
        }
    }

    var textoGlobal = ""
    fun mostrarSnackbar (text:String){
        textoGlobal += textval
        val snack = Snackbar.make(
            fiindViewById(R.id.cl_ciclo_vida),
            textGlobal,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
}