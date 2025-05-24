package com.kotlin_app.gastospush.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object NotificacaoUtil {
    fun checarNotificacao(text: String): Boolean{
        return text.contains("compra", ignoreCase = true) || text.contains("R$")
    }

    fun formatarData(data: Date) : String{
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(data)
    }

    fun formatarValor(valor: Double) : String{
        return "%.2f".format(valor)
    }
}