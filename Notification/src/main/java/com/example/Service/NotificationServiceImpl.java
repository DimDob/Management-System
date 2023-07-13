package com.example.Service;

import com.example.RabbitMQ.Consumer.EmailSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    EmailSender emailSender;

    public void deserialize(SpecificRecord event) {
        String registeredEvent = "Event registered for epic -  " + event.toString() + " : [event={ " + event.getSchema().getName() + " }]";
        log.info(registeredEvent);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode json = objectMapper.readTree(event.toString());

            String eventType = event.getSchema().getName();

            String message = switch (eventType) {
                case "EpicCreated" -> "You have been assigned with a new task: " + json.get("name");
                case "EpicModified" -> "The epic you are working on has been modified to: " + json.get("name");
                case "EpicDeleted" -> "The epic you are working on " + json.get("name") + " has been deleted";
                default -> "";
            };

            emailSender.sendMail(String.valueOf(json.get("assignee")), String.valueOf(json.get("name")), message);

        } catch (JsonProcessingException e) {
            log.info("Couldn't deserialize object");
        }

    }

}
