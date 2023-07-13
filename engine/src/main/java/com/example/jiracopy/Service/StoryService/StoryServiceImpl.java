package com.example.jiracopy.Service.StoryService;


import com.example.jiracopy.Entity.Epic.Epic;
import com.example.jiracopy.Entity.Feature.Feature;
import com.example.jiracopy.Entity.Story.Story;
import com.example.jiracopy.Repository.EpicsRepository;
import com.example.jiracopy.Service.FeaturesServices.FeaturesService;
import lombok.extern.slf4j.Slf4j;
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
public class StoryServiceImpl implements StoryService {


    private final EpicsRepository epicsRepository;

    private final FeaturesService featuresService;

    public StoryServiceImpl(EpicsRepository epicsRepository, FeaturesService featuresService) {
        this.epicsRepository = epicsRepository;
        this.featuresService = featuresService;
    }


    @Override
    public List<Story> save(UUID epicId, String featureName, Story story) {
        return saveStoryInDatabase(epicId, featureName, story);
    }

    @Override
    public List<Story> update(UUID epicId, String featureName, String storyTitle, Story story) {
        return updateStory(epicId, featureName, story);
    }

    @Override
    public Page<Story> all(UUID epicId, String featureName, Pageable pageable) {
        return allStoriesInDatabase(epicId, featureName, pageable);
    }

    @Override
    public List<Story> delete(UUID epicId, String featureName, String storyTitle) {
        return deleteStory(epicId, featureName, storyTitle);
    }

    @Override
    public Story patch(UUID epicId, String featureName, String title, Map<Object, Object> fields) {
        return patchStory(epicId, featureName, title, fields);
    }

    public Optional<Story> get(UUID epicId, String featureName, String title) {
        return getStory(epicId, featureName, title);
    }

    @Transactional
    private List<Story> saveStoryInDatabase(UUID epicId, String featureName, Story story) {
        Optional<Epic> epic = epicsRepository.findByEpicId(epicId);

        if (epic.isPresent()) {
            log.info("Saving story in the database, related to feature -> {}", featureName);
            Optional<Feature> feature = featuresService.find(epicId, featureName);
            feature.ifPresent(value -> value.getStories().add(story));
            epicsRepository.save(epic.get());
            return feature.get().getStories();
        }
        throw new IllegalArgumentException(messageStoryPresentInDatabase(story));

    }


    private Page<Story> allStoriesInDatabase(UUID epicId, String featureName, Pageable pageable) {
        log.info("Fetching feature [feature={}].", featureName);
        Optional<Epic> epic = epicsRepository.findByEpicId(epicId);

        if (epic.isPresent()) {
            Optional<Feature> feature = featuresService.find(epicId, featureName);
            if (feature.isPresent()) {
                Page<Story> storiesPage = new PageImpl<>(feature.get().getStories(), pageable, 1000);
                return storiesPage;
            }
        }
        throw new IllegalArgumentException("Feature has not been found");
    }

    @Transactional
    private List<Story> updateStory(UUID epicId, String featureName, Story story) {
        Optional<Epic> epic = epicsRepository.findByEpicId(epicId);
        if (epic.isPresent()) {
            Optional<Feature> feature = featuresService.find(epicId, featureName);

            if (feature.isPresent()) {
                for (Story s : feature.get().getStories()) {
                    if (Objects.equals(s.getTitle(), story.getTitle())) {
                        s.setFeature(feature.get());
                        s.setFunctionality(story.getFunctionality());
                        s.setPriority(story.getPriority());
                        epicsRepository.save(epic.get());
                        log.info("Story [existingStory={}] has been updated to [updatedStory={}]", s, story);
                        return feature.get().getStories();
                    }
                }
            }

        }
        throw new IllegalArgumentException("Story " + story + "has not been found in the database.");
    }

    @Transactional
    private List<Story> deleteStory(UUID epicId, String featureName, String title) {
        Optional<Epic> epic = epicsRepository.findByEpicId(epicId);

        if (epic.isPresent()) {
            Optional<Feature> feature = featuresService.find(epicId, featureName);
            if (feature.isPresent()) {
                log.info("Removing [story={}] from feature [feature={}] of epic [epic={}]", title, featureName, epicId);
                for (Story s : feature.get().getStories()) {
                    if (Objects.equals(s.getTitle(), title)) {
                        feature.get().getStories().remove(s);//f -> current feature | s -> current story
                        epicsRepository.save(epic.get());
                        return feature.get().getStories();
                    }
                }
            }

        }
        throw new IllegalArgumentException("Story not present in the features of epic " + epicId);

    }

    private Optional<Story> getStory(UUID epicId, String featureName, String title) {
        Optional<Epic> epic = epicsRepository.findByEpicId(epicId);
        Optional<Feature> feature = featuresService.find(epicId, featureName);
        if (epic.isPresent() && feature.isPresent()) {
            for (Story s : feature.get().getStories()) {
                if (Objects.equals(s.getTitle(), title)) {
                    return Optional.of(s);
                }
            }
        }
        return Optional.empty();
    }

    @Transactional
    private Story patchStory(UUID epicId, String featureName, String title, Map<Object, Object> fields) {
        Optional<Epic> existingEpic = epicsRepository.findByEpicId(epicId);
        Optional<Story> story = get(epicId, featureName, title);
        if (existingEpic.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Story.class, (String) key);
                if (field != null) {
                    log.info("Entity field found -> {}", field.getName());
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, story.get(), value);
                }

            });
            log.info("Updating [story={}] of feature [feature={}] of epic [epicId={}]to [story={}]", title, featureName, epicId, story);
            epicsRepository.save(existingEpic.get());
            return story.get();
        } else {
            String message = "Feature of epic" + epicId + " has not been found in the database";
            throw new IllegalArgumentException(message);
        }

    }


    public String messageStoryPresentInDatabase(Story story) {
        return "Feature " + story.getTitle() + " is already saved in the database.";
    }
}
