package com.example.meuremediov2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class AlarmeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
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

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(canal)
        }

        val notificacao = NotificationCompat.Builder(context, canalId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Hora do Remédio 💊")
            .setContentText("Está na hora de tomar seu medicamento.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(context).notify(1, notificacao)
        }
    }
}
