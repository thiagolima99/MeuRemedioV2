package com.example.meuremediov2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaMedicamentos : AppCompatActivity() {

    private val viewModel: MedicamentoViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MedicamentoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_medicamentos)

        recyclerView = findViewById(R.id.recyclerViewMedicamentos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = MedicamentoAdapter(emptyList()) { medicamento ->

            AlertDialog.Builder(this)
                .setTitle("Remover Medicamento")
                .setMessage("Deseja remover o medicamento '${medicamento.nome}'?")
                .setPositiveButton("Sim") { _, _ ->
                    viewModel.deletar(medicamento)
                    Toast.makeText(this, "Removido com sucesso!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        recyclerView.adapter = adapter

        // Observa a lista no banco e atualiza automaticamente
        viewModel.listaMedicamentos.observe(this) { lista ->
            adapter.atualizarLista(lista)
        }
    }
}
