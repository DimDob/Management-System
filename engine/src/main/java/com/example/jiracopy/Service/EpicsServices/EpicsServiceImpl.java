package com.example.jiracopy.Service.EpicsServices;

import com.example.jiracopy.Entity.Epic.Epic;
import com.example.jiracopy.Repository.EpicsRepository;
import com.example.jiracopy.Service.MessageController.EpicsEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
public class EpicsServiceImpl implements EpicsService {

    private final EpicsRepository epicsRepository;

    private final EpicsEventPublisher epicsEventPublisher;

    @Autowired
    public EpicsServiceImpl(EpicsRepository epicsRepository, EpicsEventPublisher epicsEventPublisher) {
        this.epicsRepository = epicsRepository;
        this.epicsEventPublisher = epicsEventPublisher;
    }


    @Override
    public Epic save(Epic epic) {
        return saveEpics(epic);
    }

    @Override
    public Page<Epic> fetch(Pageable pageable) {
        return fetchEpics((PageRequest) pageable);
    }

    @Override
    public Epic delete(UUID epicId) {
        return deleteEpicById(epicId);
    }

    @Override
    public Epic find(UUID epicID) {
        return findById(epicID);
    }

    @Override
    public Epic update(UUID epicId, Epic epic) {
        return updateEpic(epicId, epic);
    }

    @Override
    public Epic patch(UUID epicId, Map<Object, Object> field) {
        return patchEpicEntity(epicId, field);
    }

    @Transactional
    private Epic saveEpics(Epic epic) {
        epicsRepository.save(epic);
        log.info("Saving [epic={}] to the Epics table.", epic.getEpicId());
        epicsEventPublisher.publishEpicCreated(epic);
        return epic;
    }


    @Transactional
    private Page<Epic> fetchEpics(PageRequest pageable) {
        int totalElements = (int) epicsRepository.count();
        int pageSize = Integer.MAX_VALUE;
        int pageNumber = pageable.getPageNumber();

        pageable = PageRequest.of(pageNumber, pageSize);
        List<Epic> epics = epicsRepository.findAll(pageable).getContent();
        Page<Epic> page = new PageImpl<>(epics, pageable, totalElements);

        return page;
    }


    @Transactional
    private Epic deleteEpicById(UUID epicId) {
        Optional<Epic> epic = epicsRepository.findByEpicId(epicId);
        if (epic.isPresent()) {
            log.info("Deleting [epicId={}] from the database...", epicId);
            epicsRepository.deleteById(epicId);
            epicsEventPublisher.publishEpicDeleted(epic.get());
            return epic.get();
        } else {
            throw new IllegalArgumentException(epicIdNotFound(epicId));

        }
    }

    @Transactional
    private Epic findById(UUID epicId) {
        if (epicsRepository.existsById(epicId)) {
            log.info("Returning Epic by [epicId={}]", epicId);
            return epicsRepository.findByEpicId(epicId).get();
        } else {
            throw new IllegalArgumentException(epicIdNotFound(epicId));
        }
    }


    @Transactional
    private Epic updateEpic(UUID epicId, Epic epic) {
        Optional<Epic> existingEpic = epicsRepository.findByEpicId(epicId);

        if (existingEpic.isPresent()) {
            Epic updatedEpic = existingEpic.get();
            updatedEpic.setEpicId(epic.getEpicId());
            updatedEpic.setName(epic.getName());
            log.info("Updating [epicId={}] to [epicId={}]", epicId, existingEpic.get());
            epicsRepository.save(updatedEpic);
            epicsEventPublisher.publishEpicModified(updatedEpic);
            return updatedEpic;

        } else {
            throw new IllegalArgumentException(epicIdNotFound(epicId));
        }

    }

    @Transactional
    private Epic patchEpicEntity(UUID id, Map<Object, Object> fields) {
        Optional<Epic> existingEpic = epicsRepository.findByEpicId(id);

        if (existingEpic.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Epic.class, (String) key);
                if (field != null) {
                    log.info("Entity field found -> {}", field.getName());
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, existingEpic.get(), value);
                }
            });
            Epic updatedEpic = existingEpic.get();
            log.info("Updating [epicId={}] to [epicId={}]", id, existingEpic.get());
            epicsRepository.save(updatedEpic);
            return updatedEpic;
        } else {
            throw new IllegalArgumentException("Epic not found in database");
        }
    }


    private String epicIdNotFound(UUID epicId) {
        return "EpicId " + epicId + " has not been found in the database.";
    }
}
