package com.example.ccgr12024b_gasm.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ccgr12024b_gasm.R
import com.example.ccgr12024b_gasm.dao.PedidoDAO
import com.example.ccgr12024b_gasm.model.Pedido

class PedidoFormActivity : AppCompatActivity() {

    private lateinit var pedidoDAO: PedidoDAO
    private lateinit var etDescripcion: EditText
    private lateinit var etEstado: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_form)

        val clienteId = intent.getIntExtra("clienteId", -1)
        if (clienteId == -1) {
            Toast.makeText(this, "Error: Cliente no seleccionado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        pedidoDAO = PedidoDAO(this)
        etDescripcion = findViewById(R.id.etDescripcionPedido)
        etEstado = findViewById(R.id.etEstadoPedido)
        btnGuardar = findViewById(R.id.btnGuardarPedido)

        btnGuardar.setOnClickListener {
            guardarPedido(clienteId)
        }
    }

    private fun guardarPedido(clienteId: Int) {
        val descripcion = etDescripcion.text.toString()
        val estado = etEstado.text.toString()

        if (descripcion.isNotEmpty() && estado.isNotEmpty()) {
            val nuevoPedido = Pedido(
                id = 0,
                clienteId = clienteId,
                descripcion = descripcion,
                estado = estado
            )
            pedidoDAO.insertarPedido(nuevoPedido)
            Toast.makeText(this, "Pedido guardado", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
