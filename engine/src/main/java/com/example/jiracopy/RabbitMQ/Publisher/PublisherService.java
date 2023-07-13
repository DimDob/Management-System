package com.example.jiracopy.RabbitMQ.Publisher;

import org.apache.avro.specific.SpecificRecord;

public interface PublisherService {

    void publish(SpecificRecord event);
}
