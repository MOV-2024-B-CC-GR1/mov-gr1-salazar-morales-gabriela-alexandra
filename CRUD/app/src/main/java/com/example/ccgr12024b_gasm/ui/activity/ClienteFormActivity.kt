package com.example.ccgr12024b_gasm.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ccgr12024b_gasm.R
import com.example.ccgr12024b_gasm.dao.ClienteDAO
import com.example.ccgr12024b_gasm.model.Cliente
import java.util.Date

class ClienteFormActivity : AppCompatActivity() {

    private lateinit var clienteDAO: ClienteDAO
    private lateinit var etNombre: EditText
    private lateinit var etEmail: EditText
    private lateinit var etTelefono: EditText
    private lateinit var switchActivo: Switch
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_form)

        clienteDAO = ClienteDAO(this)

        etNombre = findViewById(R.id.etNombre)
        etEmail = findViewById(R.id.etEmail)
        etTelefono = findViewById(R.id.etTelefono)
        switchActivo = findViewById(R.id.switchActivo)
        btnGuardar = findViewById(R.id.btnGuardarCliente)

        // Revisar si se recibió un cliente para editar
        val cliente = intent.getSerializableExtra("cliente") as? Cliente
        if (cliente != null) {
            cargarDatosCliente(cliente)
        }

        btnGuardar.setOnClickListener {
            if (validarCampos()) {
                if (cliente != null) {
                    actualizarCliente(cliente)
                } else {
                    agregarCliente()
                }
            }
        }
    }

    private fun cargarDatosCliente(cliente: Cliente) {
        etNombre.setText(cliente.nombre)
        etEmail.setText(cliente.email)
        etTelefono.setText(cliente.telefono)
        switchActivo.isChecked = cliente.activo
    }

    private fun validarCampos(): Boolean {
        if (etNombre.text.toString().isEmpty()) {
            etNombre.error = "El nombre es obligatorio"
            return false
        }
        if (etEmail.text.toString().isEmpty()) {
            etEmail.error = "El email es obligatorio"
            return false
        }
        if (etTelefono.text.toString().isEmpty()) {
            etTelefono.error = "El teléfono es obligatorio"
            return false
        }
        return true
    }

    private fun agregarCliente() {
        val nuevoCliente = Cliente(
            id = 0,
            nombre = etNombre.text.toString(),
            email = etEmail.text.toString(),
            telefono = etTelefono.text.toString(),
            activo = switchActivo.isChecked,
            fechaRegistro = Date() // Usar la fecha actual
        )
        val resultado = clienteDAO.insertarCliente(nuevoCliente)
        if (resultado > 0) {
            Toast.makeText(this, "Cliente agregado con éxito", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error al agregar cliente", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualizarCliente(cliente: Cliente) {
        cliente.nombre = etNombre.text.toString()
        cliente.email = etEmail.text.toString()
        cliente.telefono = etTelefono.text.toString()
        cliente.activo = switchActivo.isChecked
        val resultado = clienteDAO.actualizarCliente(cliente)
        if (resultado > 0) {
            Toast.makeText(this, "Cliente actualizado con éxito", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error al actualizar cliente", Toast.LENGTH_SHORT).show()
        }
    }


}
