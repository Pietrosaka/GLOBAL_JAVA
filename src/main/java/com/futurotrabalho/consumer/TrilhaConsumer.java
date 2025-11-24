package com.futurotrabalho.consumer;

package com.futurotrabalho.consumer;

import com.futurotrabalho.config.RabbitMQConfig;
import com.futurotrabalho.model.dto.TrilhaRequestDTO;
import com.futurotrabalho.service.TrilhaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class TrilhaConsumer {
    
    private final TrilhaService trilhaService;
    
    @RabbitListener(queues = RabbitMQConfig.QUEUE_TRILHAS)
    public void processarTrilha(Map<String, Object> message) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> requestMap = (Map<String, Object>) message.get("request");
            
            // Construir DTO manualmente a partir do map
            TrilhaRequestDTO request = new TrilhaRequestDTO();
            if (requestMap.get("usuarioId") != null) {
                request.setUsuarioId(Long.valueOf(requestMap.get("usuarioId").toString()));
            }
            request.setObjetivo((String) requestMap.get("objetivo"));
            if (requestMap.get("nivel") != null) {
                try {
                    request.setNivel(TrilhaRequestDTO.NivelTrilha.valueOf(requestMap.get("nivel").toString()));
                } catch (IllegalArgumentException e) {
                    log.warn("Nível inválido, usando INTERMEDIARIO como padrão");
                    request.setNivel(TrilhaRequestDTO.NivelTrilha.INTERMEDIARIO);
                }
            }
            if (requestMap.get("duracaoMaximaHoras") != null) {
                request.setDuracaoMaximaHoras(Integer.valueOf(requestMap.get("duracaoMaximaHoras").toString()));
            }
            
            log.info("Consumindo mensagem de trilha para usuário {}", request.getUsuarioId());
            trilhaService.processarTrilha(request);
        } catch (Exception e) {
            log.error("Erro ao processar trilha: {}", e.getMessage(), e);
            // Em produção, enviar para DLQ
            throw e;
        }
    }
}

