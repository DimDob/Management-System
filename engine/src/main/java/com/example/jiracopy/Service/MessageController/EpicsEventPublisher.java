package com.example.jiracopy.Service.MessageController;

import com.example.jiracopy.Entity.Epic.Epic;
import com.example.jiracopy.RabbitMQ.Publisher.PublisherService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import schemas.messaging.avro.events.epics.created.EpicCreated;
import schemas.messaging.avro.events.epics.deleted.EpicDeleted;
import schemas.messaging.avro.events.epics.modified.EpicModified;

@Component
public class EpicsEventPublisher {

    private final PublisherService publisherService;

    public EpicsEventPublisher(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Transactional
    public void publishEpicCreated(Epic epic) {
        EpicCreated epicCreated = EpicCreated.newBuilder()
            .setId(epic.getEpicId()
            .toString())
            .setName(epic.getName())
            .setAssignee(epic.getAssignee())
            .build();
        publisherService.publish(epicCreated);
    }

    @Transactional
    public void publishEpicModified(Epic epic) {
        EpicModified epicModified = EpicModified.newBuilder()
            .setId(epic.getEpicId().toString())
            .setName(epic.getName())
            .setAssignee(epic.getAssignee())
            .setIsModified(true)
            .build();
        publisherService.publish(epicModified);
    }

    @Transactional
    public void publishEpicDeleted(Epic epic){
        EpicDeleted epicDeleted = EpicDeleted.newBuilder()
                .setId(epic.getEpicId().toString())
                .setName(epic.getName())
                .setAssignee(epic.getAssignee())
                .setIsDeleted(true)
                .build();
        publisherService.publish(epicDeleted);
    }



}
