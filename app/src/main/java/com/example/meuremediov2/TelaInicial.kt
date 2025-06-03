package com.example.meuremediov2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TelaInicial : AppCompatActivity() {

    companion object {
        const val EXTRA_TIPO_MEDICAMENTO = "TIPO_MEDICAMENTO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicial)

        val buttonComprimido = findViewById<Button>(R.id.buttonComprimido)
        val buttonGotas = findViewById<Button>(R.id.buttonGotas     )
        val buttonInalacao = findViewById<Button>(R.id.buttonInalacao)
        val buttonInjecao = findViewById<Button>(R.id.buttonInjecao)

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
    }

    private fun navegarParaMain(tipoMedicamento: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(EXTRA_TIPO_MEDICAMENTO, tipoMedicamento)
        }
        startActivity(intent)
    }
}
