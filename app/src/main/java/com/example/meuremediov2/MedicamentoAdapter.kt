package com.example.meuremediov2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meuremediov2.data.Medicamento

class MedicamentoAdapter(
    private var lista: List<Medicamento>,
    private val onItemLongClick: (Medicamento) -> Unit
) : RecyclerView.Adapter<MedicamentoAdapter.MedicamentoViewHolder>() {

    class MedicamentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.textNome)
        val tipo: TextView = itemView.findViewById(R.id.textTipo)
        val frequencia: TextView = itemView.findViewById(R.id.textFrequencia)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicamentoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicamento, parent, false)
        return MedicamentoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MedicamentoViewHolder, position: Int) {
        val medicamento = lista[position]
        holder.nome.text = medicamento.nome
        holder.tipo.text = medicamento.tipo
        holder.frequencia.text = "A cada ${medicamento.frequenciaHoras} horas"

        holder.itemView.setOnLongClickListener {
            onItemLongClick(medicamento)
            true
        }
    }

    override fun getItemCount() = lista.size

    // Atualiza a lista quando muda
    fun atualizarLista(novaLista: List<Medicamento>) {
        lista = novaLista
        notifyDataSetChanged()
    }
}
