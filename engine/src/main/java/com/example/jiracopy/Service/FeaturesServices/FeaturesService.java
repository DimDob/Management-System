package com.example.jiracopy.Service.FeaturesServices;

import com.example.jiracopy.Entity.Feature.Feature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface FeaturesService {

    Feature save(UUID epicId, Feature feature);


    Optional<Feature> find(UUID epicId, String featureName);

    Page<Feature> findAll(UUID epicId, Pageable pageable);

    Feature update(UUID epicId, Feature feature);

    List<Feature> delete(UUID epicId, String featureName);

    Feature patch(UUID epicId, String featureName, Map<Object, Object> fields);
}
