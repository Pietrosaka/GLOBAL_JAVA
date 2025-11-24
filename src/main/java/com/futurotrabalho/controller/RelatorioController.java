package com.futurotrabalho.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
@Tag(name = "Relatórios", description = "Relatórios e tendências do mercado de trabalho")
public class RelatorioController {
    
    @GetMapping("/tendencias")
    @Operation(summary = "Relatório de tendências", 
               description = "Retorna relatório de tendências do mercado de trabalho (cacheável por 1h)")
    @Cacheable(value = "relatorios", key = "'tendencias'")
    public ResponseEntity<Map<String, Object>> getTendencias() {
        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("skillsMaisDemandadas", "Java, Python, JavaScript, Spring Boot");
        relatorio.put("tendenciaRemoto", "75% das vagas são remotas ou híbridas");
        relatorio.put("crescimentoIA", "Aumento de 40% na demanda por skills de IA");
        relatorio.put("requalificacao", "60% dos profissionais buscam requalificação");
        relatorio.put("timestamp", java.time.LocalDateTime.now());
        return ResponseEntity.ok(relatorio);
    }
}

