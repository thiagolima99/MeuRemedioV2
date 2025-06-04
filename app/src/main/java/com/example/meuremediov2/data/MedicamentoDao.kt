package com.example.meuremediov2.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface MedicamentoDao {
    @Insert
    suspend fun insert(medicamento: Medicamento)

    @Delete
    suspend fun delete(medicamento: Medicamento)

    @Query("SELECT * FROM medicamentos")
    fun getAll(): LiveData<List<Medicamento>>
}

