package com.kotlin_app.gastospush.ui.screen

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kotlin_app.gastospush.data.model.Gasto
import com.kotlin_app.gastospush.service.NotificationMonitorService
import com.kotlin_app.gastospush.util.NotificacaoUtil
import java.time.Instant
import java.util.Date
import kotlin.math.max

@Composable
fun TelaPrincipal(){
    val gastos = remember { mutableStateListOf<Gasto>() }
    val notificacaoUtil = NotificacaoUtil
    val listState = rememberLazyListState()

    // Efeito que monitora mudanças na lista - Scroll
    LaunchedEffect(gastos.size) {
        if (gastos.isNotEmpty()) {
            // Scroll suave para o final
            listState.animateScrollToItem(gastos.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trilha dos gastos")}
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Simula uma notificação de gasto
                gastos.add(
                    // TODO: CAPTURAR A NOTIFICAÇÃO E TRATAR AQUI
                    Gasto(
                        descricaoCompra = "Compra no app",
                        valorCompra = (5..50).random().toDouble(),
                        dataCompra = Date.from(Instant.now())
                    )
                )
            }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar")
            }
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth(),
                elevation = 8.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "Total: ${notificacaoUtil.formatarValor(gastos.sumOf { it.valorCompra })}",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) {
        padding ->
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .systemBarsPadding() // Respeita a barra de sistema
        ) {
            //Text("GASTOS", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn (
                state = listState,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(gastos) { gasto -> GastoItem(gasto, notificacaoUtil) }
            }
        }
    }
}

@Composable
fun TelaPermissaoNotificacao() {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Permissão de Notificação") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Para capturar notificações de compras, ative o acesso.")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                context.startActivity(intent)
            }) {
                Text("Ativar acesso às notificações")
            }
        }
    }
}

@Composable
fun GastoItem(gasto: Gasto, notificacaoUtil: NotificacaoUtil){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val notificacao = "Compra: " + "R$" + notificacaoUtil.formatarValor(gasto.valorCompra)// SERÁ SUBSTITUÍDO PELO CONTEÚDO DO PUSH NOTIFICATION - IMPLEMENTAR UM SERVIÇO PARA ISSO
            if (notificacaoUtil.checarNotificacao(gasto.descricaoCompra)){
                Text(notificacao)
                Text(notificacaoUtil.formatarData(gasto.dataCompra))
            }
        }
    }
}