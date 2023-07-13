package com.example.jiracopy.Controller;


import com.example.jiracopy.Entity.Feature.Feature;
import com.example.jiracopy.Service.FeaturesServices.FeaturesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
public class FeaturesController {

    private final FeaturesService featuresService;

    @PostMapping("/epics/{epic_id}/features")
    public Feature createFeature(@PathVariable UUID epic_id, @RequestBody Feature feature) {
        return featuresService.save(epic_id, feature);
    }

    @GetMapping("/epics/{epicId}/features")
    public Page<Feature> showFeatures(@PathVariable UUID epicId, Pageable pageable) {
        return featuresService.findAll(epicId, pageable);
    }

    @GetMapping("/epics/{epicId}/features/{featureName}")
    public Optional<Feature> findFeatureByName(@PathVariable UUID epicId, @PathVariable String featureName) {
        return featuresService.find(epicId, featureName);
    }

    @PutMapping("epics/{epicId}/features/{featureName}")
    public Feature updateFeature(@PathVariable UUID epicId, @RequestBody Feature feature) {
        return featuresService.update(epicId, feature);
    }

    @DeleteMapping("epics/{epicId}/features/{featureName}")
    public List<Feature> deleteFeature(@PathVariable UUID epicId, @PathVariable String featureName) {
        return featuresService.delete(epicId, featureName);
    }

    @PatchMapping("/epics/{epicId}/features/{featureName}")
    public Feature patchFeature(@PathVariable UUID epicId, @PathVariable String featureName, @RequestBody Map<Object, Object> fields) {
        return featuresService.patch(epicId, featureName, fields);
    }


}
