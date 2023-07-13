package com.example.jiracopy.Service.integration

import com.example.jiracopy.Controller.EpicsController
import com.example.jiracopy.Controller.FeaturesController
import com.example.jiracopy.Controller.StoryController
import com.example.jiracopy.Entity.Epic.Epic
import com.example.jiracopy.Entity.Feature.Feature
import com.example.jiracopy.Entity.Story.Story
import com.example.jiracopy.Repository.EpicsRepository
import com.example.jiracopy.Service.FeaturesServices.FeaturesServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional

class StoryControllerIntegrationTest extends IntegrationTest {

    @Autowired
    EpicsRepository epicsRepository;

    @Autowired
    FeaturesServiceImpl featuresService;

    @Autowired
    FeaturesController featuresController;

    @Autowired
    EpicsController epicsController;

    @Autowired
    StoryController storyController;

    def cleanup() {
        epicsRepository.deleteAll()
    }


    @Transactional
    def "Test if createStory() creates a story and saves it to an existing feature of an existing epic"() {
        given:

        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")

        Feature feature = new Feature(name: "First feature", description: "Some feature", status: "COMPLETED", owner: "Unknown")

        Story story = new Story(title: "New story", functionality: "Unknown", priority: "Unknown")

        epicsController.newEpic(epic)
        def listOfEpics = epicsRepository.findAll()
        def lastEpic = listOfEpics.get(listOfEpics.size() - 1)


        featuresController.createFeature(lastEpic.getEpicId(), feature)

        when:
        storyController.createStory(lastEpic.getEpicId(), feature.getName(), story)

        then:
        def expectedEpic = epicsRepository.findByEpicId(lastEpic.getEpicId())
        def expectedFeature = featuresService.find(expectedEpic.get().getEpicId(), feature.getName())
        def expectedStory = expectedFeature.get().getStories().get(0)

        expectedFeature.get().getStories().size() == 1
        expectedStory.getTitle() == story.getTitle()
        expectedStory.getFunctionality() == story.getFunctionality()
        expectedStory.getPriority() == story.getPriority()
    }

    def "Test createStory() will throw and exception if neither the epic nor the feature exist"() {
        when:
        storyController.createStory(UUID.randomUUID(), "random name", new Story(title: "New story", functionality: "Unknown", priority: "Unknown"))
        then:
        thrown(IllegalArgumentException)
    }


    @Transactional
    def "Test all() will return all stories when both epic & feature exist"() {
        given:
        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")

        Feature feature = new Feature(name: "First feature", description: "Some feature", status: "COMPLETED", owner: "Unknown")

        Story story = new Story(title: "New story", functionality: "Unknown", priority: "Unknown")
        def pageable = PageRequest.of(0, 1000)

        epicsController.newEpic(epic)
        def listOfEpics = epicsRepository.findAll()
        def lastEpic = listOfEpics.get(listOfEpics.size() - 1)

        featuresController.createFeature(lastEpic.getEpicId(), feature)
        storyController.createStory(lastEpic.getEpicId(), feature.getName(), story)

        when:
        def expectedPage = storyController.all(lastEpic.getEpicId(), feature.getName(), pageable)

        then:
        def expectedEpic = epicsRepository.findByEpicId(lastEpic.getEpicId())
        def expectedFeature = featuresService.find(expectedEpic.get().getEpicId(), feature.getName())
        expectedPage.getContent() == expectedFeature.get().getStories()
    }

    def "Test all() will throw an exception if the epic does not exist"() {
        when:
        storyController.all(UUID.randomUUID(), "Random name", PageRequest.of(0, 1000))
        then:
        thrown(IllegalArgumentException)
    }

    @Transactional
    def "Test  updateStory() if the story will be updated when epic & feature are present"() {
        given:
        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")

        Feature feature = new Feature(name: "First feature", description: "Some feature", status: "COMPLETED", owner: "Unknown")

        Story story = new Story(title: "New story", functionality: "Unknown", priority: "Unknown")
        def pageable = PageRequest.of(0, 1000)

        epicsController.newEpic(epic)

        def listOfEpics = epicsRepository.findAll()
        def lastEpic = listOfEpics.get(listOfEpics.size() - 1)

        featuresController.createFeature(lastEpic.getEpicId(), feature)
        storyController.createStory(lastEpic.getEpicId(), feature.getName(), story)

        Story updatedStory = new Story(title: "New story", functionality: "Updated", priority: "Updated2")

        when:
        storyController.updateStory(lastEpic.getEpicId(), feature.getName(), story.getTitle(), updatedStory)

        then:
        def expectedEpic = epicsRepository.findByEpicId(lastEpic.getEpicId())

        def existingFeature = featuresService.find(expectedEpic.get().getEpicId(), feature.getName())

        def resultStory = existingFeature.get().getStories()
        resultStory.get(0).getTitle() == updatedStory.getTitle()
        resultStory.get(0).getFunctionality() == updatedStory.getFunctionality()
        resultStory.get(0).getPriority() == updatedStory.getPriority()
    }

    def "Test if updateStory() will throw an exception if epic & feature are not present"() {
        when:
        storyController.updateStory(UUID.randomUUID(), "Random name", "Random story", new Story(title: "New story", functionality: "some functionality", priority: "random"))
        then:
        thrown(IllegalArgumentException)
    }

    @Transactional
    def "Test deleteStory() when epic & feature are present in database"() {
        given:
        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")

        Feature feature = new Feature(name: "First feature", description: "Some feature", status: "COMPLETED", owner: "Unknown")

        Story story = new Story(title: "New story", functionality: "Unknown", priority: "Unknown")

        epicsController.newEpic(epic)

        def listOfEpics = epicsRepository.findAll()
        def lastEpic = listOfEpics.get(listOfEpics.size() - 1)

        featuresController.createFeature(lastEpic.getEpicId(), feature)
        storyController.createStory(lastEpic.getEpicId(), feature.getName(), story)

        when:

        storyController.deleteStory(lastEpic.getEpicId(), feature.getName(), story.getTitle())

        then:

        def expectedEpic = epicsRepository.findByEpicId(lastEpic.getEpicId())
        expectedEpic != Optional.empty()

        def expectedFeature = featuresService.find(expectedEpic.get().getEpicId(), feature.getName())
        expectedFeature != Optional.empty()

        expectedFeature.get().getStories().size() == 0
    }

    def "Test if deleteStory() will throw exception if epic & feature are not present"() {
        when:
        storyController.deleteStory(UUID.randomUUID(), "Random feature", "Random story")

        then:
        thrown(IllegalArgumentException)
    }

    @Transactional
    def "Test if patchStory() will patch a field of an existing story with epic & feature present in the database"() {
        given:
        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")

        Feature feature = new Feature(name: "First feature", description: "Some feature", status: "COMPLETED", owner: "Unknown")

        Story story = new Story(title: "New story", functionality: "Unknown", priority: "Unknown")

        epicsController.newEpic(epic)

        def listOfEpics = epicsRepository.findAll()
        def lastEpic = listOfEpics.get(listOfEpics.size() - 1)

        featuresController.createFeature(lastEpic.getEpicId(), feature)
        storyController.createStory(lastEpic.getEpicId(), feature.getName(), story)

        Map<Object, Object> fields = [title        : "New story",
                                      functionality: "Patched"]

        when:
        def patchedStory = storyController.patchStory(lastEpic.getEpicId(), feature.getName(), story.getTitle(), fields)

        then:
        def expectedEpic = epicsRepository.findByEpicId(lastEpic.getEpicId())
        expectedEpic != Optional.empty()

        def expectedFeature = featuresService.find(expectedEpic.get().getEpicId(), feature.getName())
        expectedFeature != Optional.empty()

        def expectedStory = expectedFeature.get().getStories().get(0)

        expectedStory.getTitle() == patchedStory.getTitle()
        expectedStory.getFunctionality() == patchedStory.getFunctionality()
        expectedStory.getPriority() == patchedStory.getPriority()
    }

    def "Test whether will patchStory() throw an exception when epic & feature are not present in database"() {
        when:
        storyController.patchStory(UUID.randomUUID(), "Random feature", "Random title", [name: "Random story", priority: "Random priority"])

        then:
        thrown(IllegalArgumentException)
    }


}