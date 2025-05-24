package com.kotlin_app.gastospush.data.model

import java.util.Date

data class NotificacaoPush (
    val titulo : String,
    val texto : String,
    val valorItem: Double,
    val dataLancamento: Date
)