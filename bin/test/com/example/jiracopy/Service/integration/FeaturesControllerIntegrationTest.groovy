package com.example.jiracopy.Service.integration

import com.example.jiracopy.Controller.EpicsController
import com.example.jiracopy.Controller.FeaturesController
import com.example.jiracopy.Entity.Epic.Epic
import com.example.jiracopy.Entity.Feature.Feature
import com.example.jiracopy.Repository.EpicsRepository
import com.example.jiracopy.Service.FeaturesServices.FeaturesServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional

class FeaturesControllerIntegrationTest extends IntegrationTest {

    @Autowired
    EpicsRepository epicsRepository;

    @Autowired
    FeaturesServiceImpl featuresService;

    @Autowired
    FeaturesController featuresController;

    @Autowired
    EpicsController epicsController;

    def cleanup() {
        epicsRepository.deleteAll()
    }

    @Transactional
    def "Test if feature is correctly attached to an epic when created"() {
        given:
        Feature feature = new Feature(name: "First feature", description: "Some feature", status: "COMPLETED", owner: "Unknown")

        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")

        epicsController.newEpic(epic)
        def listOfEpics = epicsRepository.findAll()
        def lastEpic = listOfEpics.get(listOfEpics.size() - 1)

        when:
        featuresController.createFeature(lastEpic.getEpicId(), feature)
        then:
        def expectedEpic = epicsRepository.findByEpicId(lastEpic.getEpicId()).get()
        expectedEpic.features.size() == 1
        expectedEpic.features.get(0).getName() == feature.getName()
        expectedEpic.features.get(0).getOwner() == feature.getOwner()
        expectedEpic.features.get(0).getDescription() == feature.getDescription()
        expectedEpic.features.get(0).getStatus() == feature.getStatus()
    }

    def "Test if an exception is thrown when a feature is requested to be saved into an epic which does not exist"() {
        given:
        Feature feature = new Feature(name: "First feature", description: "Some feature", status: "COMPLETED", owner: "Unknown")

        when:
        featuresController.createFeature(UUID.randomUUID(), feature)

        then:
        thrown(IllegalArgumentException)
    }

    @Transactional
    def "Test if showFeatures() returns all features in the database"() {
        given:
        Feature feature1 = new Feature(name: "First feature", description: "Some feature", status: "COMPLETED", owner: "Unknown")
        Feature feature2 = new Feature(name: "Second feature", description: "Some feature2", status: "COMPLETED", owner: "Unknown2")

        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")

        epicsController.newEpic(epic)

        def listOfEpics = epicsRepository.findAll()
        def lastEpic = listOfEpics.get(listOfEpics.size() - 1)

        featuresController.createFeature(lastEpic.getEpicId(), feature1)
        featuresController.createFeature(lastEpic.getEpicId(), feature2)

        def pageable = PageRequest.of(0, 1000)
        when:
        def expectedPage = featuresController.showFeatures(lastEpic.getEpicId(), pageable)
        then:
        def expectedEpic = epicsRepository.findByEpicId(lastEpic.getEpicId())
        expectedEpic instanceof Optional<Epic>
        expectedEpic != null
        expectedPage.getContent() == expectedEpic.get().getFeatures()
    }

    def "Test if showFeatures() throws an exception if an epic is not existing in the database"() {
        when:
        featuresController.showFeatures(UUID.randomUUID(), PageRequest.of(0, 1000))
        then:
        thrown(IllegalArgumentException)
    }

    @Transactional
    def "Test if findFeatureByName() returns the proper feature from the passed epic"() {
        given:
        Feature feature = new Feature(name: "First feature", description: "Some feature", status: "COMPLETED", owner: "Unknown")

        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")

        epicsController.newEpic(epic)

        def listOfEpics = epicsRepository.findAll()
        def lastEpic = listOfEpics.get(listOfEpics.size() - 1)
        featuresController.createFeature(lastEpic.getEpicId(), feature)

        when:
        featuresController.findFeatureByName(lastEpic.getEpicId(), feature.getName())
        then:
        def expectedEpic = epicsRepository.findByEpicId(lastEpic.getEpicId())
        def expectedFeature = expectedEpic.get().getFeatures().get(0)

        expectedFeature.getName() == feature.getName()
        expectedFeature.getStatus() == feature.getStatus()
        expectedFeature.getDescription() == feature.getDescription()
        expectedFeature.getOwner() == feature.getOwner()
    }

    def "Test if findFeatureByName() will return an empty optional instance  if the epic which is requested to return a feature is empty"() {
        when:
        def feature = featuresController.findFeatureByName(UUID.randomUUID(), "Not existing feature")
        then:
        feature == Optional.empty()
    }

    @Transactional
    def "Test if updateFeature() is correctly updating a feature of an existing epic"() {
        given:
        Feature feature = new Feature(name: "First feature", description: "Some feature", status: "COMPLETED", owner: "Unknown")

        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")

        epicsController.newEpic(epic)


        def listOfEpics = epicsRepository.findAll()
        def lastEpic = listOfEpics.get(listOfEpics.size() - 1)
        featuresController.createFeature(lastEpic.getEpicId(), feature)

        Feature updatedFeature = new Feature(name: "First feature", description: "Some feature update", status: "COMPLETED", owner: "Unknown")

        when:
        featuresController.updateFeature(lastEpic.getEpicId(), updatedFeature)

        then:
        def expectedEpic = epicsRepository.findByEpicId(lastEpic.getEpicId())
        def epicFeature = expectedEpic.get().getFeatures().get(0)

        epicFeature.getOwner() == updatedFeature.getOwner()
        epicFeature.getStatus() == updatedFeature.getStatus()
        epicFeature.getName() == updatedFeature.getName()
        epicFeature.getDescription() == updatedFeature.getDescription()
    }

    def "Test if updateFeature() will throw an exception if the requested epic does not exist"() {
        when:
        featuresController.updateFeature(UUID.randomUUID(), new Feature(name: "First feature", description: "Some feature update", status: "COMPLETED", owner: "Unknown"))
        then:
        thrown(IllegalArgumentException)
    }

    @Transactional
    def "Test if delete() will delete an existing feature of an existing epic"() {
        given:
        Feature feature = new Feature(name: "First feature", description: "Some feature", status: "COMPLETED", owner: "Unknown")

        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")

        epicsController.newEpic(epic)


        def listOfEpics = epicsRepository.findAll()
        def lastEpic = listOfEpics.get(listOfEpics.size() - 1)
        featuresController.createFeature(lastEpic.getEpicId(), feature)

        when:
        featuresController.deleteFeature(lastEpic.getEpicId(), feature.getName())

        then:
        def expectedEpic = epicsRepository.findByEpicId(lastEpic.getEpicId())
        featuresService.find(expectedEpic.get().getEpicId(), feature.getName()) == Optional.empty()
        expectedEpic.get().getFeatures().size() == 0
    }

    def "Test if delete() will throw an exception if neither the feature nor the epic exist"() {
        when:
        featuresController.deleteFeature(UUID.randomUUID(), "Some random name")
        then:
        thrown(IllegalArgumentException)
    }

    @Transactional
    def "Test if patchFeature() will patch a field of an existing feature entity of an existing epic"() {
        given:
        Feature feature = new Feature(name: "First feature", description: "Some feature", status: "COMPLETED", owner: "Unknown")

        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")

        epicsController.newEpic(epic)


        def listOfEpics = epicsRepository.findAll()
        def lastEpic = listOfEpics.get(listOfEpics.size() - 1)
        featuresController.createFeature(lastEpic.getEpicId(), feature)

        Map<Object, Object> fields = [name       : "First feature",
                                      description: "Some patched description",
                                      status     : "COMPLETED",
                                      owner      : "Unknown"]

        when:
        featuresController.patchFeature(lastEpic.getEpicId(), feature.getName(), fields)

        then:
        def expectedEpic = epicsRepository.findByEpicId(lastEpic.getEpicId())
        def expectedPatchedFeature = expectedEpic.get().getFeatures().get(0)

        expectedPatchedFeature.getName() == feature.getName()
        expectedPatchedFeature.getStatus() == feature.getStatus()
        expectedPatchedFeature.getOwner() == feature.getOwner()
        expectedPatchedFeature.getDescription() == "Some patched description"
    }

    @Transactional
    def "Test if patchFeature() will throw an exception if the patched feature does not exist"() {
        given:
        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")
        epicsController.newEpic(epic)

        Feature feature = new Feature(name: "First feature", description: "Random", owner: "Random", status: "COMPLETED")
        Map<Object, Object> fields = [name       : "First feature",
                                      description: "Some patched description",
                                      status     : "COMPLETED",
                                      owner      : "Unknown"]

        def listOfEpics = epicsRepository.findAll()
        def lastEpic = listOfEpics.get(listOfEpics.size() - 1)

        featuresService.save(lastEpic.getEpicId(), feature)

        when:
        featuresController.patchFeature(lastEpic.getEpicId(), "Random feature", fields)
        then:
        thrown(IllegalArgumentException)
    }


}