package com.fibra.backendfibra.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

@Component
public class KeepAliveScheduler {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${keepalive.url}")
    private String keepAliveUrl;

    @Value("${keepalive.token}")
    private String keepAliveToken;

    @Scheduled(fixedRate = 300000) // 5 minutos em milissegundos
    public void keepAlive() {
        try {
            // Adicionando parâmetros de paginação obrigatórios
            String urlComPaginacao = "https://fibra.onrender.com/services?page=1&size=10";
            ResponseEntity<String> response = restTemplate.getForEntity(urlComPaginacao, String.class);
        } catch (Exception e) {
            // Loga a exceção, mas não interrompe o agendamento
            System.err.println("[KeepAliveScheduler] Falha ao fazer requisição keep-alive: " + e.getMessage());
        }
    }
}
