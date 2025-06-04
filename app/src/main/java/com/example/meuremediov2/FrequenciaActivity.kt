package com.example.meuremediov2

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.meuremediov2.data.Medicamento
import java.util.*

class FrequenciaActivity : AppCompatActivity() {

    private lateinit var textViewHorarioSelecionado: TextView
    private lateinit var textViewInfo: TextView
    private var horaSelecionada = -1
    private var minutoSelecionado = -1

    private lateinit var nomeMedicamento: String
    private lateinit var tipoMedicamento: String

    //ViewModel do banco de dados
    private val viewModel: MedicamentoViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frequencia)

        // ⏰ Permissão para notificação
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 100)
        }

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(this, "Permita alarmes nas configurações", Toast.LENGTH_LONG).show()
                startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
            }
        }

        textViewInfo = findViewById(R.id.textViewInfoFrequencia)
        textViewHorarioSelecionado = findViewById(R.id.textViewHorarioSelecionado)

        val buttonSelecionarHorario = findViewById<Button>(R.id.buttonSelecionarHorario)
        val buttonFinalizar = findViewById<Button>(R.id.buttonFinalizar)

        nomeMedicamento = intent.getStringExtra(MainActivity.EXTRA_NOME_MEDICAMENTO) ?: "Desconhecido"
        tipoMedicamento = intent.getStringExtra(MainActivity.EXTRA_TIPO_MEDICAMENTO) ?: "Desconhecido"

        textViewInfo.text = """
            Informações do medicamento:
            
            Nome: $nomeMedicamento
            Tipo: $tipoMedicamento
        """.trimIndent()

        // ⏰ Abre o relógio
        buttonSelecionarHorario.setOnClickListener {
            abrirTimePicker()
        }

        buttonFinalizar.setOnClickListener {
            if (horaSelecionada == -1 || minutoSelecionado == -1) {
                Toast.makeText(this, "Selecione um horário primeiro!", Toast.LENGTH_SHORT).show()
            } else {
                // 🔥 Salva o remédio no banco
                val medicamento = Medicamento(
                    nome = nomeMedicamento,
                    tipo = tipoMedicamento,
                    frequenciaHoras = 2
                )

                viewModel.adicionar(medicamento)

                agendarAlarme()
                Toast.makeText(this, "Medicamento salvo com sucesso!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, TelaInicial::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    // 🕐 Abre o relógio
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

    // 🔔 Agendar alarme
    private fun agendarAlarme() {
        val calendario = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, horaSelecionada)
            set(Calendar.MINUTE, minutoSelecionado)
            set(Calendar.SECOND, 0)
        }

        // Se horário já passou hoje agenda para amanhã
        if (calendario.before(Calendar.getInstance())) {
            calendario.add(Calendar.DATE, 1)
        }

        val intent = Intent(this, AlarmeReceiver::class.java).apply {
            putExtra(MainActivity.EXTRA_NOME_MEDICAMENTO, nomeMedicamento)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(
                    this,
                    "Permita agendar alarmes nas configurações.",
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
            "Alarme definido para ${String.format("%02d:%02d", horaSelecionada, minutoSelecionado)}",
            Toast.LENGTH_LONG
        ).show()
    }
}
