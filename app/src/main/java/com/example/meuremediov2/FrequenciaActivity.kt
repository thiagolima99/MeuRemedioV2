package com.example.meuremediov2

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import android.provider.Settings

class FrequenciaActivity : AppCompatActivity() {

    private lateinit var textViewHorarioSelecionado: TextView
    private var horaSelecionada = -1
    private var minutoSelecionado = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frequencia)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 100)
        }

        val textViewInfo = findViewById<TextView>(R.id.textViewInfoFrequencia)
        val buttonSelecionarHorario = findViewById<Button>(R.id.buttonSelecionarHorario)
        val buttonFinalizar = findViewById<Button>(R.id.buttonFinalizar)
        textViewHorarioSelecionado = findViewById(R.id.textViewHorarioSelecionado)

        textViewInfo.text = "Configurar horário do seu medicamento"

        buttonSelecionarHorario.setOnClickListener {
            abrirTimePicker()
        }

        buttonFinalizar.setOnClickListener {
            agendarAlarme()
        }
    }

    private fun abrirTimePicker() {
        val calendario = Calendar.getInstance()
        val horaAtual = calendario.get(Calendar.HOUR_OF_DAY)
        val minutoAtual = calendario.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(
            this,
            { _, hora, minuto ->
                horaSelecionada = hora
                minutoSelecionado = minuto
                textViewHorarioSelecionado.text = String.format("%02d:%02d", hora, minuto)
            },
            horaAtual,
            minutoAtual,
            true
        )
        timePicker.show()
    }

    private fun agendarAlarme() {
        if (horaSelecionada == -1 || minutoSelecionado == -1) {
            Toast.makeText(this, "Selecione um horário primeiro!", Toast.LENGTH_SHORT).show()
            return
        }

        val calendario = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, horaSelecionada)
            set(Calendar.MINUTE, minutoSelecionado)
            set(Calendar.SECOND, 0)
        }

        if (calendario.before(Calendar.getInstance())) {
            calendario.add(Calendar.DATE, 1)
        }

        val intent = Intent(this, AlarmeReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(
                    this,
                    "Permita agendar alarmes exatos nas configurações.",
                    Toast.LENGTH_LONG
                ).show()

                val intentPermissao = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intentPermissao)
                return
            }
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendario.timeInMillis,
            pendingIntent
        )

        Toast.makeText(
            this,
            "Alarme definido para ${
                String.format(
                    "%02d:%02d",
                    horaSelecionada,
                    minutoSelecionado
                )
            }",
            Toast.LENGTH_LONG
        ).show()
    }
}

