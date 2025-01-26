package com.example.ccgr12024b_gasm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ccgr12024b_gasm.ui.clientes.ClientesActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Asociar el layout activity_main.xml

        // Referencia al botón btnClientes
        val btnClientes: Button = findViewById(R.id.btnClientes)

        // Configurar la acción al hacer clic en el botón
        btnClientes.setOnClickListener {
            val intent = Intent(this, ClientesActivity::class.java)
            startActivity(intent)
        }
    }
}
