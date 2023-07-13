package com.example.RabbitMQ.Consumer;

import com.example.Service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

    @Value("${rabbitmq.queue.create}")
    private String onCreateQueue;

    @Value("${rabbitmq.queue.modify}")
    private String onModifyQueue;

    @Value("${rabbitmq.queue.delete}")
    private String onDeleteQueue;

    @Autowired
    NotificationService notificationService;

    @RabbitListener(queues = {"${rabbitmq.queue.create}", "${rabbitmq.queue.modify}", "${rabbitmq.queue.delete}"})
    public void consumeEpicCreatedEvent(SpecificRecord event) {

        String fromQueue = event.getSchema().getName();
        switch (fromQueue) {
            case "EpicCreated" -> {
                log.info("Event received [event={}]", fromQueue);
                log.info("Event consumed [event={}] in queue [queue={}]", event, onCreateQueue);
                notificationService.deserialize(event);
            }
            case "EpicModified" -> {
                log.info("Event received [event={}]", fromQueue);
                log.info("Event consumed [event={}] in queue [queue={}]", event, onModifyQueue);
                notificationService.deserialize(event);
            }
            case "EpicDeleted" -> {
                log.info("Event received [event={}]", fromQueue);
                log.info("Event consumed [event={}] in queue [queue={}]", event, onDeleteQueue);
                notificationService.deserialize(event);
            }
        }


    }


}
