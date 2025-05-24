package com.kotlin_app.gastospush.ui.screen

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kotlin_app.gastospush.data.model.NotificacaoPush
import com.kotlin_app.gastospush.data.repository.NotificacaoRepository
import com.kotlin_app.gastospush.util.NotificacaoUtil

@Composable
fun TelaPrincipal(){
    val notificacaoUtil = NotificacaoUtil
    val listState = rememberLazyListState()
    val notificacoesPush = NotificacaoRepository.notificacoes

    // Efeito que monitora mudanças na lista - Scroll
    LaunchedEffect(notificacoesPush.size) {
        if (notificacoesPush.isNotEmpty()) {
            // Scroll suave para o final
            listState.animateScrollToItem(notificacoesPush.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trilha dos gastos")}
            )
        }/*,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Simula uma notificação de gasto
                gastos.add(
                    // TODO: CAPTURAR A NOTIFICAÇÃO E TRATAR AQUI
                    Gasto(
                        descricaoCompra = "Compra",
                        valorCompra = (5..50).random().toDouble(),
                        dataCompra = Date.from(Instant.now())
                    )
                )
            }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar")
            }
        }*/,
        bottomBar = {
            Surface(
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth(),
                elevation = 8.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "Total: ${notificacaoUtil.formatarValor(notificacoesPush.sumOf { it.valorItem })}",
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
                items(notificacoesPush) { notificacao -> Item(notificacao) }
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
fun Item(notificacao: NotificacaoPush){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(notificacao.titulo)
            Text(notificacao.texto)
        }
    }
}