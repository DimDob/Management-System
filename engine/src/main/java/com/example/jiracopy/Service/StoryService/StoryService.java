package com.example.jiracopy.Service.StoryService;

import com.example.jiracopy.Entity.Story.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface StoryService {

    List<Story> save(UUID epicId, String featureName, Story story);

    Page<Story> all(UUID epicId, String featureName, Pageable pageable);

    List<Story> update(UUID epicId, String featureName, String storyTitle, Story story);

    List<Story> delete(UUID epicId, String featureName, String storyTitle);

    Story patch(UUID epicId, String featureName, String title, Map<Object, Object> fields);
}
