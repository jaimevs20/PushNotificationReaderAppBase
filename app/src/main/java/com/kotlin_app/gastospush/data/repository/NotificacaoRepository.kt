package com.kotlin_app.gastospush.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.kotlin_app.gastospush.data.model.NotificacaoPush

object NotificacaoRepository {
    val notificacoes = mutableStateListOf<NotificacaoPush>()

    fun adicionar(notificacao: NotificacaoPush) {
        notificacoes.add(notificacao)
    }
}
