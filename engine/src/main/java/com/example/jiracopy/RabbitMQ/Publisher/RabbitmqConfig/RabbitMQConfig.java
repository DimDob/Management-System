package com.example.jiracopy.RabbitMQ.Publisher.RabbitmqConfig;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.create}")
    private String queue;

    @Value("${rabbitmq.exchange.name}")
    private String topicExchange;

    @Value("${rabbitmq.routing.key.onCreate}")
    private String onCreateRoutingKey;

    @Bean
    public Queue queue() {
        return new Queue(queue, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicExchange);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with(onCreateRoutingKey);
    }
}
