package com.example.jiracopy.Service.unitTests

import com.example.jiracopy.Entity.Epic.Epic
import com.example.jiracopy.Entity.Feature.Feature
import com.example.jiracopy.Entity.Story.Story
import com.example.jiracopy.Repository.EpicsRepository
import com.example.jiracopy.Service.FeaturesServices.FeaturesServiceImpl
import com.example.jiracopy.Service.StoryService.StoryServiceImpl
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class StoryServiceTest extends Specification {

    def epicsRepository = Mock(EpicsRepository.class)
    def featureService = new FeaturesServiceImpl(epicsRepository)
    def storyService = new StoryServiceImpl(epicsRepository, featureService)


    def "Test createStory() with epic & feature present in database"() {
        given:
        def epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")
        def feature = new Feature(name: "Random feature", status: "COMPLETED", description: "Random")
        epic.features.add(feature)
        def story = new Story(title: "Random title", priority: "Random")

        when:
        def resultStory = storyService.save(epic.getEpicId(), feature.getName(), story)

        then:
        epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)
        featureService.find(epic.getEpicId(), feature.getName()) >> Optional.of(feature)

        resultStory == feature.stories
        feature.stories.contains(story)
        epicsRepository.save(epic) >> epic
    }

    def "Test whether will createStory() throw an exception when no epic & feature are present"() {
        given:
        epicsRepository.findByEpicId(_) >> Optional.empty()
        featureService.find(UUID.randomUUID(), "Random feature") >> Optional.empty()

        when:
        storyService.save(UUID.randomUUID(), "Random feature", new Story(title: "Random story"))
        then:
        thrown(IllegalArgumentException)
    }

    def "Test update() when epic & feature & story are present"() {
        given:
        def epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")
        def feature = new Feature(name: "Random feature", status: "COMPLETED", description: "Random")
        epic.features.add(feature)
        def story = new Story(title: "Random title", priority: "Random")
        def updateStory = new Story(title: "Random title", priority: "Updated")

        epic.features.add(feature)
        feature.stories.add(story)

        when:
        def resultStory = storyService.update(epic.getEpicId(), feature.getName(), story.getTitle(), updateStory)

        then:
        epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)
        featureService.find(epic.getEpicId(), feature.getName()) >> Optional.of(feature)

        resultStory == feature.stories
        resultStory.get(0).getTitle() == feature.stories.get(0).getTitle()
        resultStory.get(0).getPriority() == feature.stories.get(0).getPriority()
        resultStory.get(0).getFunctionality() == feature.stories.get(0).getFunctionality()
    }

    def "Test whether updateStory() will throw an exception when epic & feature are not present in database"() {
        given:
        epicsRepository.findByEpicId(_) >> Optional.empty()
        featureService.find(UUID.randomUUID(), "Random feature") >> Optional.empty()

        when:
        storyService.update(UUID.randomUUID(), "Random feature", "Random story", new Story(title: "Random story", priority: "Updated"))
        then:
        thrown(IllegalArgumentException)
    }

    def "Test if all() will return accurate stories saved in database when epic & feature are present"() {
        given:
        def epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")
        def feature = new Feature(name: "Random feature", status: "COMPLETED", description: "Random")
        epic.features.add(feature)
        def story = new Story(title: "Random title", priority: "Random")

        epic.features.add(feature)
        feature.stories.add(story)

        when:
        def pageStory = storyService.all(epic.getEpicId(), feature.getName(), PageRequest.of(0, 1000))
        then:
        epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)
        featureService.find(epic.getEpicId(), feature.getName()) >> Optional.of(feature)

        pageStory instanceof Page
        pageStory.content == feature.stories
    }

    def "Test if all() method will throw an exception if epic & feature are not present"() {
        given:
        epicsRepository.findByEpicId(_) >> Optional.empty()
        featureService.find(UUID.randomUUID(), "Random feature") >> Optional.empty()

        when:
        storyService.all(UUID.randomUUID(), "Random feature", PageRequest.of(0, 1000))

        then:
        thrown(IllegalArgumentException)
    }

    def "Test if delete() will delete the stored story entity when epic & feature are present"() {
        given:
        def epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")
        def feature = new Feature(name: "Random feature", status: "COMPLETED", description: "Random")
        epic.features.add(feature)
        def story = new Story(title: "Random title", priority: "Random")

        epic.features.add(feature)
        feature.stories.add(story)

        when:
        def deletedStory = storyService.delete(epic.getEpicId(), feature.getName(), story.getTitle())

        then:
        epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)
        featureService.find(epic.getEpicId(), feature.getName()) >> Optional.of(feature)

        deletedStory.size() == feature.stories.size()
    }

    def "Test if delete() will thrw an exception if epic & feature are not present"() {
        given:
        epicsRepository.findByEpicId(_) >> Optional.empty()
        featureService.find(UUID.randomUUID(), "Random feature") >> Optional.empty()

        when:
        storyService.delete(UUID.randomUUID(), "Random feature", "Random story")

        then:
        thrown(IllegalArgumentException)
    }

    def "Test if patch() will patch a specific field of a story when epic & feature are present"() {
        given:
        def epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")
        def feature = new Feature(name: "Random feature", status: "COMPLETED", description: "Random")
        epic.features.add(feature)
        def story = new Story(title: "Random title", priority: "Random")

        Map<Object, Object> fields = [title   : "Random title",
                                      priority: "Patched priority"]

        epic.features.add(feature)
        feature.stories.add(story)

        when:
        def patchedStory = storyService.patch(epic.getEpicId(), feature.getName(), story.getTitle(), fields)

        then:
        epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)
        featureService.find(epic.getEpicId(), feature.getName()) >> Optional.of(feature)

        patchedStory.getTitle() == story.getTitle()
        patchedStory.getPriority() == "Patched priority"
        patchedStory.getFunctionality() == story.getFunctionality()
    }

    def "Test if patch() will throw an exception when epic & feature are not present"() {
        given:
        epicsRepository.findByEpicId(_) >> Optional.empty()
        featureService.find(UUID.randomUUID(), "Random feature") >> Optional.empty()

        Map<Object, Object> fields = [title   : "Random title",
                                      priority: "Patched priority"]

        when:
        storyService.patch(UUID.randomUUID(), "Random feature", "Random story", fields)

        then:
        thrown(IllegalArgumentException)
    }

    def "Test if get() will return the correct story when epic & feature are present"() {
        given:
        def epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")
        def feature = new Feature(name: "Random feature", status: "COMPLETED", description: "Random")
        epic.features.add(feature)
        def story = new Story(title: "Random title", priority: "Random")

        epic.features.add(feature)
        feature.stories.add(story)

        when:
        def returnedStory = storyService.get(epic.getEpicId(), feature.getName(), story.getTitle())

        then:
        epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)
        featureService.find(epic.getEpicId(), feature.getName()) >> Optional.of(feature)

        returnedStory != Optional.empty()
        returnedStory.get().getTitle() == story.getTitle()
        returnedStory.get().getFunctionality() == story.getFunctionality()
        returnedStory.get().getPriority() == story.getPriority()
    }

    def "Test if get() will return empty instance when epic & feature are not present"() {
        given:
        epicsRepository.findByEpicId(_) >> Optional.empty()
        featureService.find(UUID.randomUUID(), "Random feature") >> Optional.empty()


        when:
        def optionalStory = storyService.get(UUID.randomUUID(), "Random feature", "Random title")
        then:
        optionalStory == Optional.empty()

    }

}


