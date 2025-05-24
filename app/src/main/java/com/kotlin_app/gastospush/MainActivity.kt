package com.kotlin_app.gastospush

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.kotlin_app.gastospush.service.NotificationMonitorService
import com.kotlin_app.gastospush.ui.screen.TelaPermissaoNotificacao
import com.kotlin_app.gastospush.ui.screen.TelaPrincipal
import kotlinx.coroutines.delay
import androidx.compose.runtime.setValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            var habilitado by remember { mutableStateOf(isNotificacaoServiceHabilitado(context)) }

            // Reage quando o app volta e a permissão muda
            LaunchedEffect(Unit) {
                while (!habilitado) {
                    delay(1000)
                    habilitado = isNotificacaoServiceHabilitado(context)
                }
            }

            if(habilitado){
                TelaPrincipal()
            } else {
                TelaPermissaoNotificacao()
            }
        }
    }

    fun isNotificacaoServiceHabilitado(context: Context): Boolean {
        val cn = ComponentName(context, NotificationMonitorService::class.java)
        val enabledListeners = Settings.Secure.getString(
            context.contentResolver,
            "enabled_notification_listeners"
        )
        return enabledListeners?.contains(cn.flattenToString()) == true
    }


}