package com.example.meuremediov2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOME_MEDICAMENTO = "NOME_MEDICAMENTO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tipo = intent.getStringExtra(TelaInicial.EXTRA_TIPO_MEDICAMENTO)

        findViewById<TextView>(R.id.textViewTipoSelecionadoInfo).text =
            "Tipo: ${tipo ?: "Não informado"}"

        val nomeInput = findViewById<EditText>(R.id.editTextNomeMedicamento)

        findViewById<Button>(R.id.buttonProximoMain).setOnClickListener {
            val nome = nomeInput.text.toString().trim()
            if (nome.isNotEmpty()) {
                val intent = Intent(this, FrequenciaActivity::class.java).apply {
                    putExtra(EXTRA_NOME_MEDICAMENTO, nome)
                    putExtra(TelaInicial.EXTRA_TIPO_MEDICAMENTO, tipo)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Digite o nome do medicamento!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
