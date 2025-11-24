package com.futurotrabalho.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    public static final String QUEUE_TRILHAS = "trilhas.queue";
    public static final String QUEUE_RELATORIOS = "relatorios.queue";
    public static final String QUEUE_EMAILS = "emails.queue";
    public static final String QUEUE_DLQ = "dlq.queue";
    
    @Bean
    public Queue trilhasQueue() {
        return new Queue(QUEUE_TRILHAS, true);
    }
    
    @Bean
    public Queue relatoriosQueue() {
        return new Queue(QUEUE_RELATORIOS, true);
    }
    
    @Bean
    public Queue emailsQueue() {
        return new Queue(QUEUE_EMAILS, true);
    }
    
    @Bean
    public Queue dlqQueue() {
        return new Queue(QUEUE_DLQ, true);
    }
    
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
    
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }
}

