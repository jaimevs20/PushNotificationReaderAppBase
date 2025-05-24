package com.kotlin_app.gastospush

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.kotlin_app.gastospush.service.NotificationMonitorService
import com.kotlin_app.gastospush.ui.screen.TelaPermissaoNotificacao
import com.kotlin_app.gastospush.ui.screen.TelaPrincipal
import kotlinx.coroutines.delay
import androidx.compose.runtime.setValue
import com.kotlin_app.gastospush.data.repository.BancoRepository


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // CHAMADA DA API - EXECUTADO SEMPRE QUE A APLICAÇÃO É REINICIALIZADA
        BancoRepository.processarBancos()

        setContent {
            val context = LocalContext.current
            var habilitado by remember { mutableStateOf(isNotificacaoServiceHabilitado(context)) }

            // REAGE QUANDO O APP VOLTA E A PERMISSÃO É ACEITA
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