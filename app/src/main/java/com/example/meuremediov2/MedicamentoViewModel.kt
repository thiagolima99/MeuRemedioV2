package com.example.meuremediov2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.meuremediov2.data.AppDatabase
import com.example.meuremediov2.data.Medicamento
import kotlinx.coroutines.launch

class MedicamentoViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).medicamentoDao()

    val listaMedicamentos: LiveData<List<Medicamento>> = dao.getAll()

    fun adicionar(medicamento: Medicamento) {
        viewModelScope.launch {
            dao.insert(medicamento)
        }
    }

    fun deletar(medicamento: Medicamento) {
        viewModelScope.launch {
            dao.delete(medicamento)
        }
    }
}


