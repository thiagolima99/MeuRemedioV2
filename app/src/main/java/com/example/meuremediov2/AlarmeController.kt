package com.example.meuremediov2

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri

object AlarmeController {
    var ringtone: Ringtone? = null

    fun tocarSom(context: Context) {
        if (ringtone == null) {
            try {
                val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                    ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

                ringtone = RingtoneManager.getRingtone(context.applicationContext, alarmUri)
                ringtone?.play()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun pararSom() {
        ringtone?.stop()
        ringtone = null
    }
}
