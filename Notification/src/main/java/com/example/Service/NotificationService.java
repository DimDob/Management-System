package com.example.Service;

import org.apache.avro.specific.SpecificRecord;

public interface NotificationService {

    void deserialize(SpecificRecord event);
}
