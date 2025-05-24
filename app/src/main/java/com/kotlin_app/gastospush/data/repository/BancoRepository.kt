package com.kotlin_app.gastospush.data.repository

import android.util.Log
import com.kotlin_app.gastospush.service.NotificacaoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object BancoRepository {
    fun processarBancos() {
        CoroutineScope(Dispatchers.IO).launch {
            val resposta = NotificacaoService.obterBancos()
            Log.d("BancosLog", "Resposta: $resposta")
        }
    }
}