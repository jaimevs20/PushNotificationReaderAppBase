package com.kotlin_app.gastospush.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.kotlin_app.gastospush.data.model.NotificacaoPush
import com.kotlin_app.gastospush.data.repository.NotificacaoRepository
import com.kotlin_app.gastospush.util.NotificacaoUtil

class NotificationMonitorService : NotificationListenerService(){

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        // EXTRAINDO DADOS DA NOTIFICAÇÃO
        sbn?.notification?.extras?.let { extras ->
            val titulo = extras.getString(Notification.EXTRA_TITLE)
            val texto = extras.getString(Notification.EXTRA_TEXT)

            Log.d("NotificacaoLog", "Título: $titulo | Texto: $texto | Extras: $extras")

            if (NotificacaoUtil.checarNotificacao(texto.toString(), titulo.toString())) {
                val tituloAdd = titulo.toString()
                val textoAdd = texto.toString()

                Log.d("NotificacaoUsableLog", "Título: $tituloAdd | Texto: $textoAdd")

                NotificacaoRepository.adicionar(
                    NotificacaoPush(
                    titulo = tituloAdd,
                    texto = textoAdd,
                    valorItem =  NotificacaoUtil.extrairValor(textoAdd)
                ))
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        // Opcional
    }
}