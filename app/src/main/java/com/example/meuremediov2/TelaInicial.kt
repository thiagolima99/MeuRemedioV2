package com.example.meuremediov2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class TelaInicial : AppCompatActivity() {

    companion object {
        const val EXTRA_TIPO_MEDICAMENTO = "TIPO_MEDICAMENTO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicial)

        val buttonListaMedicamentos = findViewById<Button>(R.id.buttonListaMedicamentos)
        val buttonComprimido = findViewById<Button>(R.id.buttonComprimido)
        val buttonGotas = findViewById<Button>(R.id.buttonGotas)
        val buttonInalacao = findViewById<Button>(R.id.buttonInalacao)
        val buttonInjecao = findViewById<Button>(R.id.buttonInjecao)
        val buttonPararAlarme = findViewById<Button>(R.id.buttonPararAlarme)

        buttonComprimido.setOnClickListener {
            navegarParaMain("Comprimido")
        }

        buttonGotas.setOnClickListener {
            navegarParaMain("Gotas")
        }

        buttonInalacao.setOnClickListener {
            navegarParaMain("Inalação")
        }

        buttonInjecao.setOnClickListener {
            navegarParaMain("Injeção")
        }

        buttonPararAlarme.setOnClickListener {
            pararSom()
        }

        buttonListaMedicamentos.setOnClickListener {
            val intent = Intent(this, ListaMedicamentos::class.java)
            startActivity(intent)
        }
    }

    // Desliga o alarme
    private fun pararSom() {
        AlarmeController.pararSom()
        Toast.makeText(this, "Alarme desligado", Toast.LENGTH_SHORT).show()
    }

    private fun navegarParaMain(tipoMedicamento: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(EXTRA_TIPO_MEDICAMENTO, tipoMedicamento)
        }
        startActivity(intent)
    }
}
