package com.kotlin_app.gastospush.service;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class NotificacaoService {

    public static String obterBancos(){
        String resultado = "vazio";
        try {
            URL url = new URL("https://brasilapi.com.br/api/banks/v1");
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            StringBuilder resposta = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null) {
                resposta.append(linha);
            }
            reader.close();
            resultado = resposta.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }
}
