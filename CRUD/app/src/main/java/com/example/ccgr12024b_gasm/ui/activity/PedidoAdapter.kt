package com.example.ccgr12024b_gasm.ui.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ccgr12024b_gasm.R
import com.example.ccgr12024b_gasm.model.Pedido

class PedidoAdapter(
    private val pedidos: List<Pedido>,
    private val onEdit: (Pedido) -> Unit,
    private val onDelete: (Pedido) -> Unit
) : RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = pedidos[position]
        holder.bind(pedido)
        holder.btnEdit.setOnClickListener { onEdit(pedido) }
        holder.btnDelete.setOnClickListener { onDelete(pedido) }
    }

    override fun getItemCount(): Int = pedidos.size

    class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        private val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
        val btnEdit: Button = itemView.findViewById(R.id.btnEditPedido)
        val btnDelete: Button = itemView.findViewById(R.id.btnDeletePedido)

        fun bind(pedido: Pedido) {
            tvDescripcion.text = "Descripción: ${pedido.descripcion}"
            tvEstado.text = "Estado: ${pedido.estado}"
        }
    }
}
