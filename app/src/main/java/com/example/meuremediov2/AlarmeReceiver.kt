package com.example.meuremediov2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.RingtoneManager
import android.media.Ringtone
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        criarNotificacao(context)
        tocarSom(context)
    }

    private fun criarNotificacao(context: Context) {
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
            .setContentText("Lembrete: Está na hora de tomar seu medicamento.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(1, notificacao)
        }
    }

    private fun tocarSom(context: Context) {
        try {
            val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val ringtone: Ringtone = RingtoneManager.getRingtone(context, alarmUri)
            ringtone.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
