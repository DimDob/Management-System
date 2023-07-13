package com.example.jiracopy.Service.EpicsServices;

import com.example.jiracopy.Entity.Epic.Epic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;


public interface EpicsService {

    Epic save(Epic epic);

    Page<Epic> fetch(Pageable pageable);

    Epic delete(UUID epicId);

    Epic find(UUID epicId);

    Epic update(UUID epicId, Epic epic);

    Epic patch(UUID id, Map<Object, Object> field);

}
