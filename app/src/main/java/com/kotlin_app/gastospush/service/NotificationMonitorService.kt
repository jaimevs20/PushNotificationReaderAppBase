package com.kotlin_app.gastospush.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.kotlin_app.gastospush.util.NotificacaoUtil

class NotificationMonitorService : NotificationListenerService(){
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        // EXTRAINDO DADOS DA NOTIFICAÇÃO
        sbn?.notification?.extras?.let { extras ->
            val title = extras.getString(Notification.EXTRA_TITLE)
            val text = extras.getString(Notification.EXTRA_TEXT)

            if (NotificacaoUtil.checarNotificacao(text.toString()) || NotificacaoUtil.checarNotificacao(title.toString())) {
                Log.d("AppGastoLog", "Título: $title | Texto: $text | Extras: $extras")
                // Aqui você pode processar e armazenar o gasto
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        // Opcional
    }
}