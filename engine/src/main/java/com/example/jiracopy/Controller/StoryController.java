package com.example.jiracopy.Controller;


import com.example.jiracopy.Entity.Story.Story;
import com.example.jiracopy.Service.StoryService.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class StoryController {


    private final StoryService storyService;


    @PostMapping("epics/{epicId}/features/{featureName}/stories")
    public List<Story> createStory(@PathVariable UUID epicId, @PathVariable String featureName, @RequestBody Story story) {
        return storyService.save(epicId, featureName, story);
    }

    @GetMapping("/epics/{epicId}/features/{featureId}/stories")
    public Page<Story> all(@PathVariable UUID epicId, @PathVariable String featureId, Pageable pageable) {
        return storyService.all(epicId, featureId, pageable);
    }

    @PutMapping("epics/{epicId}/features/{featureName}/stories/{title}")
    public List<Story> updateStory(@PathVariable UUID epicId, @PathVariable String featureName, @PathVariable String title, @RequestBody Story story) {
        return storyService.update(epicId, featureName, title, story);
    }

    @DeleteMapping("epics/{epicId}/features/{featureName}/stories/{title}")
    public List<Story> deleteStory(@PathVariable UUID epicId, @PathVariable String featureName, @PathVariable String title) {
        return storyService.delete(epicId, featureName, title);
    }

    @PatchMapping("epics/{epicId}/features/{featureName}/stories/{title}")
    public Story patchStory(@PathVariable UUID epicId, @PathVariable String featureName, @PathVariable String title, @RequestBody Map<Object, Object> fields) {
        return storyService.patch(epicId, featureName, title, fields);
    }
}
