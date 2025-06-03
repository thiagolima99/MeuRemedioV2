package com.example.meuremediov2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val nomeMedicamento =
            intent.getStringExtra(MainActivity.EXTRA_NOME_MEDICAMENTO) ?: "Desconhecido"

        criarNotificacao(context, nomeMedicamento)
        AlarmeController.tocarSom(context)
    }

    private fun criarNotificacao(context: Context, nomeMedicamento: String) {
        val canalId = "canal_alarme"
        val nomeCanal = "Lembrete de Medicamento"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                canalId,
                nomeCanal,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Canal para lembretes de medicamento"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(canal)
        }

        val notificacao = NotificationCompat.Builder(context, canalId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Hora do Remédio 💊")
            .setContentText("Hora de tomar o remédio $nomeMedicamento." )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(1, notificacao)
    }
}
