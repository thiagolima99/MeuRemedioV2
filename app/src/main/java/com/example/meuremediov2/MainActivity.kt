package com.example.meuremediov2

import android.content.Intent
import android.media.Ringtone
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var ringtone: Ringtone? = null

    companion object {
        const val EXTRA_NOME_MEDICAMENTO = "NOME_MEDICAMENTO"
        const val EXTRA_TIPO_MEDICAMENTO = "TIPO_MEDICAMENTO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tipo = intent.getStringExtra(TelaInicial.EXTRA_TIPO_MEDICAMENTO) ?: "Desconhecido"

        findViewById<TextView>(R.id.textViewTipoSelecionadoInfo).text =
            "Tipo: $tipo"

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

        findViewById<Button>(R.id.buttonPararAlarme).setOnClickListener {
            AlarmeController.pararSom()
            pararSom()
        }
    }
    //Para o alarme//
    private fun pararSom() {
            ringtone?.stop()
            Toast.makeText(this, "Alarme desligado", Toast.LENGTH_SHORT).show()
        }
    }

