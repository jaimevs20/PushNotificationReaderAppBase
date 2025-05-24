package com.kotlin_app.gastospush.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object NotificacaoUtil {
    fun checarNotificacao(texto: String, titulo: String): Boolean{
        return (titulo.contains("Você enviou", ignoreCase = true)
                || titulo.contains("Você pagou", ignoreCase = true)
                || titulo.contains("Você transferiu", ignoreCase = true)
                || titulo.contains("você possui 1 mensagem", ignoreCase = true) // PADRÃO BANCO DO BR
                )
                && (
                    texto.contains("compra", ignoreCase = true)
                    || texto.contains("compra aprovada", ignoreCase = true)
                    || texto.contains("pix", ignoreCase = true)
                    || texto.contains("cartão", ignoreCase = true)
                    || texto.contains("pagamento", ignoreCase = true)
                    || texto.contains("R$"))
    }

    fun formatarData(data: Date) : String?{
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(data)
    }

    fun formatarValor(valor: Double) : String{
        return "%.2f".format(valor)
    }
    fun extrairValor(texto : String) : Double {
        val regex = Regex("R\\$\\s*(\\d+,\\d{2})")
        val match = regex.find(texto)
        return match?.groupValues?.get(1)?.replace(",", ".")?.toDoubleOrNull() ?: 0.0
    }
}