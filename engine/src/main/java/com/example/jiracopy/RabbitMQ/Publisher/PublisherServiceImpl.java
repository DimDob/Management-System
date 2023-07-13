package com.example.jiracopy.RabbitMQ.Publisher;


import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PublisherServiceImpl implements PublisherService {

    @Value("${rabbitmq.exchange.name}")
    private String topicExchange;



    @Value("${rabbitmq.queue.create}")
    private String createdEpicQueue;

    @Value("${rabbitmq.queue.modify}")
    private String modifyEpicQueue;

    @Value("${rabbitmq.queue.delete}")
    private String deleteEpicQueue;

    @Value("${rabbitmq.routing.key.onCreate}")
    private String onCreateRoutingKey;

    @Value("${rabbitmq.routing.key.onModify}")
    private String onModifyRoutingKey;

    @Value("${rabbitmq.routing.key.onDelete}")
    private String onDeleteRoutingKey;


    private RabbitTemplate rabbitTemplate;


    public PublisherServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(SpecificRecord event) {
        String toQueue = event.getSchema().getName();

        switch (toQueue) {
            case "EpicCreated" -> rabbitTemplate.convertAndSend(topicExchange, onCreateRoutingKey, event);
            case "EpicModified" -> rabbitTemplate.convertAndSend(topicExchange, onModifyRoutingKey, event);
            case "EpicDeleted" -> rabbitTemplate.convertAndSend(topicExchange, onDeleteRoutingKey, event);
        }
    }
}
