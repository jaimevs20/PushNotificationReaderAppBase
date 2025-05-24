package com.kotlin_app.gastospush.data.model

import java.util.Date

data class Gasto(
    val descricaoCompra: String,
    val valorCompra: Double,
    val dataCompra: Date
)
