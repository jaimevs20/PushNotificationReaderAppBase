package com.kotlin_app.gastospush.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.kotlin_app.gastospush.data.model.NotificacaoPush

class NotificacaoViewModel : ViewModel() {
    private val _notificacoes = mutableStateListOf<NotificacaoPush>()
    val notificacoes: List<NotificacaoPush> = _notificacoes

    fun adicionarNotificacao(notificacao: NotificacaoPush) {
        _notificacoes.add(notificacao)
    }
}
