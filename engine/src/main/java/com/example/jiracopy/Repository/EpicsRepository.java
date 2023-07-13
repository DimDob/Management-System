package com.example.jiracopy.Repository;

import com.example.jiracopy.Entity.Epic.Epic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EpicsRepository extends JpaRepository<Epic, UUID> {


    Optional<Epic> findByEpicId(UUID epicId);

}