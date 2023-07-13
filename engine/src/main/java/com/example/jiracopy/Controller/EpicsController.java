package com.example.jiracopy.Controller;

import com.example.jiracopy.Entity.Epic.Epic;
import com.example.jiracopy.Service.EpicsServices.EpicsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
public class EpicsController {


    private final EpicsService epicsService;

    @GetMapping("/epics")
    public Page<Epic> all(Pageable pageable) {
        return epicsService.fetch(pageable);
    }

    @PostMapping("/epics")
    public Epic newEpic(@RequestBody Epic newEpic) {
        return epicsService.save(newEpic);
    }

    @GetMapping("/epics/{id}")
    public Epic one(@PathVariable UUID id) {
        return epicsService.find(id);
    }


    @PutMapping("/epics/{id}")
    public Epic updateEpic(@PathVariable UUID id, @RequestBody Epic epic) {
        return epicsService.update(id, epic);
    }

    @DeleteMapping("/epics/{id}")
    public Epic deleteEpic(@PathVariable UUID id) {
        return epicsService.delete(id);
    }

    @PatchMapping("/epics/{id}")
    public Epic patchEpic(@PathVariable UUID id, @RequestBody Map<Object, Object> fields) {
        return epicsService.patch(id, fields);
    }
}