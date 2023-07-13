package com.example.jiracopy.Service.unitTests

import com.example.jiracopy.Entity.Epic.Epic
import com.example.jiracopy.Entity.Feature.Feature
import com.example.jiracopy.Repository.EpicsRepository
import com.example.jiracopy.Service.FeaturesServices.FeaturesServiceImpl
import spock.lang.Specification

class FeaturesServiceTest extends Specification {


    def epicsRepository = Mock(EpicsRepository.class)
    def featuresService = new FeaturesServiceImpl(epicsRepository)

    def "saveEpics method in EpicsService should save the created epic"() {
        given:
        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random name")

        Feature feature = new Feature(name: "First feature", status: "COMPLETED", description: "Random description")

        epicsRepository.save(epic)

        when: 'saveEpics is invoked'
        def savedFeature = featuresService.save(epic.getEpicId(), feature)

        then: 'A save transaction should be triggered'
        1 * epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)
        1 * epicsRepository.save(epic)

        savedFeature.getName() == feature.getName()
        savedFeature.getDescription() == feature.getDescription()
        savedFeature.getStories() == feature.getStories()
        savedFeature.getStatus() == feature.getStatus()

    }

    def "Throw exception when the epic is not present in the database"() {
        given:
        Epic epic = new Epic(epicId:  UUID.randomUUID(), name: "Random name")

        Feature feature = new Feature(name: "First feature", status: "COMPLETED", description: "Random description")

        epicsRepository.findByEpicId(_)>>Optional.empty()

        when:
        featuresService.save(epic.getEpicId(), feature)
        then:
        thrown(IllegalArgumentException)
    }


    def "updateFeature should update the feature of a current epic"() {
        given:
        Epic epic = new Epic()
        epic.setEpicId(UUID.randomUUID())
        epic.setName("Random name")

        Feature feature = new Feature(name: "First feature", description: "Random description", status: "COMPLETED", owner: "Random owner", epic: epic)

        epicsRepository.save(epic)
        epic.features.add(feature)

        Feature update = new Feature(name: "First feature", description: "Update", status: "COMPLETED", owner: "Unknown", epic: epic)

        when:
        def resultFeature = featuresService.update(epic.getEpicId(), update)

        then:
        1 * epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)

        resultFeature.getName() == feature.getName()
        resultFeature.getDescription() == feature.getDescription()
        resultFeature.getStatus() == feature.getStatus()
        resultFeature.getStories() == feature.getStories()
    }

    def "Expect an exception to be thrown if the epic is not present in the database"() {
        given:
        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random name")

        Feature feature = new Feature(name: "First feature", description: "Random description", status: "COMPLETED", owner: "Random", epic: epic)
        epic.features.add(feature)

        Feature updatedFeature = new Feature(name: "First feature", description: "Updated description", status: "COMPLETED", owner: "New owner")

        epicsRepository.findByEpicId(_) >> Optional.empty()

        when:
        featuresService.update(epic.getEpicId(), updatedFeature)
        then:
        thrown(IllegalArgumentException)
    }

    def "Expect delete feature to delete an existing feature"() {
        given:
        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random name")

        Feature feature = new Feature(name:"Random name", status: "COMPLETED", description: "Random", owner: 'Random', epic: epic)
        epic.features.add(feature)
        epicsRepository.save(epic)

        when:
        def deletedFeature = featuresService.delete(epic.getEpicId(), "Random feature")

        then:
        1 * epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)
        1 * epicsRepository.save(epic)

        !epic.features.contains(deletedFeature)
        deletedFeature.get(0).getName() == feature.getName()
        deletedFeature.get(0).getStatus() == feature.getStatus()
        deletedFeature.get(0).getDescription() == feature.getDescription()
        deletedFeature.get(0).getOwner() == feature.getOwner()
        deletedFeature.get(0).getStories() == feature.getStories()

    }


    def "Deleting an not existing epic should throw an exception"() {
        given:
        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random name")

        epicsRepository.findByEpicId(_) >> Optional.empty()

        when:
        featuresService.delete(epic.getEpicId(), "Random feature")
        then:
        thrown(IllegalArgumentException)
    }

    def "Test patch() method if epic exists in database"(){
        given:
        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random name")

        Feature feature = new Feature(name:"Random name", status: "COMPLETED", description: "Random", owner: 'Random', epic: epic)
        epic.features.add(feature)

        Map<Object, Object> fields = [
                name: "Random name",
                description: "Patched"
        ]

        epicsRepository.save(epic)

        when:
        def patchedFeature = featuresService.patch(epic.getEpicId(), feature.getName(), fields)
        then:
        epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)

        epic.features.contains(patchedFeature)
        patchedFeature.getName() == feature.getName()
        patchedFeature.getOwner() == feature.getOwner()
        patchedFeature.getStatus() == feature.getStatus()
        patchedFeature.getDescription() == feature.getDescription()
        patchedFeature.getStories() == feature.getStories()
    }

    def "Test patch() if epic is not in database"(){
        given:
        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random name")
        epicsRepository.save(epic)

        epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)
        featuresService.get(UUID.randomUUID(), "Random feature") >> Optional.empty()

        Map<Object, Object> fields = [
                title: "Random title",
                priority: "Patched priority"
        ]

        when:
        featuresService.patch(epic.getEpicId(), "Random feature",fields)

        then:
        thrown(IllegalArgumentException)
    }
}
