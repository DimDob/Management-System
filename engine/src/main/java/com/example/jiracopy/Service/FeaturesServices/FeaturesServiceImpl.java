package com.example.jiracopy.Service.FeaturesServices;

import com.example.jiracopy.Entity.Epic.Epic;
import com.example.jiracopy.Entity.Feature.Feature;
import com.example.jiracopy.Entity.Feature.Status;
import com.example.jiracopy.Repository.EpicsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
@Slf4j
public class FeaturesServiceImpl implements FeaturesService {
    private final EpicsRepository epicsRepository;


    @Autowired
    public FeaturesServiceImpl(EpicsRepository epicsRepository) {
        this.epicsRepository = epicsRepository;
    }


    @Override
    public Feature save(UUID epicId, Feature feature) {
        return saveFeatureInDatabase(epicId, feature);
    }

    @Override
    public Optional<Feature> find(UUID epicId, String featureName) {
        return findFeatureByName(epicId, featureName);
    }


    @Override
    public Page<Feature> findAll(UUID epicId, Pageable pageable) {
        return findAllFeatures(epicId, pageable);
    }

    @Override
    public Feature update(UUID epicId, Feature feature) {
        return updateFeature(epicId, feature);
    }

    @Override
    public List<Feature> delete(UUID epicId, String featureName) {
        return deleteFeature(epicId, featureName);
    }

    @Override
    public Feature patch(UUID epicId, String featureName, Map<Object, Object> fields) {
        return patchFeature(epicId, featureName, fields);
    }

    @Transactional
    private Feature saveFeatureInDatabase(UUID epicId, Feature feature) {
        Optional<Epic> epic = epicsRepository.findByEpicId(epicId);

        if (epic.isPresent()) {
            log.info("Feature [feature={}] saved in the database to epic [epicId={}]", feature, epicId);
            feature.setEpic(epic.get());
            epic.get().getFeatures().add(feature);
            epicsRepository.save(epic.get());
            return feature;
        } else {
            throw (new IllegalArgumentException(messageFeaturePresentInDatabase(feature))); //TODO Add custom exception
        }
    }

    public Optional<Feature> get(UUID epicId, String featureName) {
        return getFeature(epicId, featureName);
    }


    private Page<Feature> findAllFeatures(UUID epicId, Pageable pageable) {
        Optional<Epic> epic = epicsRepository.findByEpicId(epicId);
        if (epic.isPresent()) {
            Page<Feature> featuresPage = new PageImpl<>(epic.get().getFeatures(), pageable, 1000);
            return featuresPage;
        } else {
            throw new IllegalArgumentException("Epic has not been found");
        }
    }

    @Transactional
    private Feature updateFeature(UUID epicId, Feature feature) {
        Optional<Epic> existingEpic = epicsRepository.findByEpicId(epicId);

        if (existingEpic.isPresent()) {
            for (Feature f : existingEpic.get().getFeatures()) {
                if (Objects.equals(f.getName(), feature.getName())) {
                    f.setStatus(Status.valueOf(feature.getStatus()));
                    f.setOwner(feature.getOwner());
                    f.setDescription(feature.getDescription());
                }
            }
            epicsRepository.save(existingEpic.get());
            log.info("Updating [feature={}]", feature.getName());
            return feature;

        } else {
            throw new IllegalArgumentException(messageFeaturePresentInDatabase(feature));
        }
    }

    @Transactional
    private List<Feature> deleteFeature(UUID epicId, String featureName) {
        Optional<Epic> epic = epicsRepository.findByEpicId(epicId);
        if (epic.isPresent()) {
            log.info("Feature [feature={}] deleted from epic [epic={}].", featureName, epicId);
            epic.get().getFeatures().removeIf(f -> Objects.equals(f.getName(), featureName));
            epicsRepository.save(epic.get());
            return epic.get().getFeatures();
        } else {
            throw new IllegalArgumentException("Feature not found in the database");
        }

    }


    @Transactional
    private Feature patchFeature(UUID epicId, String featureName, Map<Object, Object> fields) {
        Optional<Epic> existingEpic = epicsRepository.findByEpicId(epicId);
        Optional<Feature> feature = get(epicId, featureName);
        if (existingEpic.isPresent() && feature.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Feature.class, (String) key);
                if (field != null) {
                    log.info("Entity field found -> {}", field.getName());
                    log.info("Setting key {} to value -> {}", key, value);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, feature.get(), value);
                }
            });
            Epic updatedEpic = existingEpic.get();
            log.info("Updating [featureName={}] of epic [epicId={}]to [featureName={}]", featureName, epicId, feature);
            epicsRepository.save(updatedEpic);
            return feature.get();
        } else {
            String message = "Feature of epic" + epicId + " has not been found in the database";
            throw new IllegalArgumentException(message);
        }

    }

    private Optional<Feature> findFeatureByName(UUID epicId, String featureName) {
        Optional<Epic> epic = epicsRepository.findByEpicId(epicId);
        if (epic.isPresent()) {
            for (Feature f : epic.get().getFeatures()) {
                if (Objects.equals(f.getName(), featureName)) {
                    log.info("Fetched feature - " + f);
                    return Optional.of(f);
                }
            }
        }
        log.info(featureName + "has not been found in the passed epic");
        return Optional.empty();
    }

    private Optional<Feature> getFeature(UUID epicId, String featureName) {
        Epic epic = epicsRepository.findByEpicId(epicId).get();

        for (Feature f : epic.getFeatures()) {
            if (Objects.equals(f.getName(), featureName)) {
                return Optional.of(f);
            }
        }
        return Optional.empty();
    }

    public String messageFeaturePresentInDatabase(Feature feature) {
        return "Feature " + feature.getName() + " is already saved in the database.";
    }


}
