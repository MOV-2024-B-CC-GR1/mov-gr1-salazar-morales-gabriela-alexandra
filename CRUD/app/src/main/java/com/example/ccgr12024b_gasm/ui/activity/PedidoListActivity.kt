package com.example.ccgr12024b_gasm.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ccgr12024b_gasm.R
import com.example.ccgr12024b_gasm.dao.PedidoDAO
import com.example.ccgr12024b_gasm.model.Pedido
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Toast
import android.view.View
import android.widget.TextView

class PedidoListActivity : AppCompatActivity() {

    private lateinit var pedidoDAO: PedidoDAO
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PedidoAdapter
    private lateinit var btnAgregarPedido: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_list)

        // Obtener el clienteId desde el Intent
        val clienteId = intent.getIntExtra("clienteId", -1)
        if (clienteId == -1) {
            Toast.makeText(this, "Error: Cliente no seleccionado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        pedidoDAO = PedidoDAO(this)
        recyclerView = findViewById(R.id.recyclerViewPedidos)
        btnAgregarPedido = findViewById(R.id.btnAgregarPedido)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Cargar pedidos del cliente seleccionado
        cargarPedidos(clienteId)

        btnAgregarPedido.setOnClickListener {
            val intent = Intent(this, PedidoFormActivity::class.java)
            intent.putExtra("clienteId", clienteId) // Pasar el clienteId al formulario
            startActivity(intent)
        }
    }

    private fun cargarPedidos(clienteId: Int) {
        val pedidos = pedidoDAO.obtenerPedidosPorCliente(clienteId)
        val emptyView = findViewById<TextView>(R.id.tvEmptyPedidos)

        if (pedidos.isEmpty()) {
            emptyView.visibility = View.VISIBLE // Mostrar mensaje de lista vacía
            recyclerView.visibility = View.GONE // Ocultar RecyclerView
        } else {
            emptyView.visibility = View.GONE // Ocultar mensaje de lista vacía
            recyclerView.visibility = View.VISIBLE // Mostrar RecyclerView
        }

        adapter = PedidoAdapter(pedidos, onEdit = { pedido ->
            editarPedido(pedido)
        }, onDelete = { pedido ->
            eliminarPedido(pedido, clienteId)
        })
        recyclerView.adapter = adapter
    }

    private fun editarPedido(pedido: Pedido) {
        val intent = Intent(this, PedidoFormActivity::class.java)
        intent.putExtra("pedido", pedido)
        startActivity(intent)
    }

    private fun eliminarPedido(pedido: Pedido, clienteId: Int) {
        pedidoDAO.eliminarPedido(pedido.id)
        Toast.makeText(this, "Pedido eliminado", Toast.LENGTH_SHORT).show()
        cargarPedidos(clienteId) // Recargar pedidos después de eliminar
    }

    override fun onResume() {
        super.onResume()
        val clienteId = intent.getIntExtra("clienteId", -1)
        if (clienteId != -1) {
            cargarPedidos(clienteId)
        }
    }
}
