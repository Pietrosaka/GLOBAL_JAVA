package com.futurotrabalho.service;

import com.futurotrabalho.model.entity.TrilhaAprendizado;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpringAIService {
    
    private final ChatClient chatClient;
    
    public String gerarTrilhaAprendizado(String objetivo, com.futurotrabalho.model.dto.TrilhaRequestDTO.NivelTrilha nivel, Integer duracaoMaxima) {
        try {
            String nivelStr = nivel != null ? nivel.name() : "INTERMEDIARIO";
            String duracaoStr = duracaoMaxima != null ? duracaoMaxima.toString() : "40";
            
            String promptTemplate = """
                Você é um especialista em desenvolvimento de carreira e educação profissional.
                Crie uma trilha de aprendizado personalizada com as seguintes características:
                
                Objetivo: {objetivo}
                Nível: {nivel}
                Duração máxima: {duracao} horas
                
                A trilha deve incluir:
                1. Módulos de aprendizado organizados sequencialmente
                2. Recursos recomendados (cursos, livros, vídeos)
                3. Projetos práticos
                4. Métricas de progresso
                5. Próximos passos após conclusão
                
                Formate a resposta de forma clara e estruturada, com títulos e subtítulos.
                """;
            
            PromptTemplate template = new PromptTemplate(promptTemplate);
            Prompt prompt = template.create(Map.of(
                "objetivo", objetivo != null ? objetivo : "Desenvolvimento profissional geral",
                "nivel", nivelStr,
                "duracao", duracaoStr
            ));
            
            String resposta = chatClient.call(prompt.getContents()).getResult().getOutput().getContent();
            
            log.info("Trilha gerada com sucesso via Spring AI");
            return resposta;
            
        } catch (Exception e) {
            log.error("Erro ao gerar trilha com Spring AI: {}", e.getMessage());
            // Fallback: retornar trilha genérica
            return gerarTrilhaFallback(objetivo, nivel, duracaoMaxima);
        }
    }
    
    public String simularEntrevista(String pergunta, String resposta) {
        try {
            String promptTemplate = """
                Você é um entrevistador técnico experiente. Analise a resposta do candidato e forneça:
                1. Feedback sobre a qualidade da resposta (0-100)
                2. Pontos fortes
                3. Pontos de melhoria
                4. Sugestões de como melhorar a resposta
                
                Pergunta: {pergunta}
                Resposta do candidato: {resposta}
                
                Forneça um feedback construtivo e detalhado.
                """;
            
            PromptTemplate template = new PromptTemplate(promptTemplate);
            Prompt prompt = template.create(Map.of(
                "pergunta", pergunta,
                "resposta", resposta
            ));
            
            String feedback = chatClient.call(prompt.getContents()).getResult().getOutput().getContent();
            
            log.info("Feedback de entrevista gerado com sucesso");
            return feedback;
            
        } catch (Exception e) {
            log.error("Erro ao gerar feedback de entrevista: {}", e.getMessage());
            return "Erro ao processar feedback. Por favor, tente novamente.";
        }
    }
    
    public String gerarDescricaoVaga(String titulo, String requisitos) {
        try {
            String promptTemplate = """
                Crie uma descrição de vaga profissional e atraente para:
                
                Título: {titulo}
                Requisitos: {requisitos}
                
                A descrição deve incluir:
                - Visão geral da posição
                - Responsabilidades principais
                - Requisitos técnicos e comportamentais
                - Benefícios e diferenciais
                - Cultura da empresa
                
                Seja claro, objetivo e atraente para candidatos qualificados.
                """;
            
            PromptTemplate template = new PromptTemplate(promptTemplate);
            Prompt prompt = template.create(Map.of(
                "titulo", titulo,
                "requisitos", requisitos
            ));
            
            return chatClient.call(prompt.getContents()).getResult().getOutput().getContent();
            
        } catch (Exception e) {
            log.error("Erro ao gerar descrição de vaga: {}", e.getMessage());
            return "Descrição padrão da vaga: " + titulo;
        }
    }
    
    private String gerarTrilhaFallback(String objetivo, com.futurotrabalho.model.dto.TrilhaRequestDTO.NivelTrilha nivel, Integer duracaoMaxima) {
        return String.format("""
            # Trilha de Aprendizado: %s
            
            ## Nível: %s
            ## Duração estimada: %d horas
            
            ### Módulo 1: Fundamentos
            - Introdução aos conceitos básicos
            - Recursos de aprendizado recomendados
            
            ### Módulo 2: Aplicação Prática
            - Projetos práticos
            - Exercícios de fixação
            
            ### Módulo 3: Avançado
            - Tópicos avançados
            - Projetos complexos
            
            ### Próximos Passos
            - Continue praticando
            - Participe de comunidades
            - Busque mentoria
            """, objetivo, nivel != null ? nivel.name() : "INTERMEDIARIO", duracaoMaxima != null ? duracaoMaxima : 40);
    }
}

