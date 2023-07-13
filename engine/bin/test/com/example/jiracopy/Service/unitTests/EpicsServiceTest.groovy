package com.example.jiracopy.Service.unitTests

import com.example.jiracopy.Entity.Epic.Epic
import com.example.jiracopy.Repository.EpicsRepository
import com.example.jiracopy.Service.EpicsServices.EpicsServiceImpl
import com.example.jiracopy.Service.MessageController.EpicsEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class EpicsServiceTest extends Specification {


    def epicsRepository = Mock(EpicsRepository.class)
    def epicsEventPublisher = Mock(EpicsEventPublisher.class)
    def epicsService = new EpicsServiceImpl(epicsRepository, epicsEventPublisher)

    def "saveEpics method in EpicsService should save the created epic"() {
        given:
        Epic epic = new Epic()
        epic.setEpicId(UUID.randomUUID())
        epic.setName("Random name")


        when: 'saveEpics is invoked'
        def savedEpic = epicsService.save(epic)

        then: 'A save transaction should be triggered'
        savedEpic.getEpicId() == epic.getEpicId()
        savedEpic.getName() == epic.getName()
        savedEpic.getFeatures() == epic.getFeatures()
    }


    def "fetchEpics method should return the saved epics"() {
        given:
        Epic epic = new Epic()
        epic.setEpicId(UUID.randomUUID())
        epic.setName("Random name")

        Page<Epic> page = new PageImpl<>([epic], PageRequest.of(0, 1000), 20)

        when:
        epicsService.fetch(PageRequest.of(0, 1000)) >> Optional.empty()
        then: 'All of the entities should be returned'
        def epics = epicsRepository.findAll(PageRequest.of(0, 1000))
        page.every() == epics.every()
    }

    def "Test if deleteById() deletes an existing epic"() {
        given:
        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic")
        epicsRepository.save(epic)

        when:
        def deletedEpic = epicsService.delete(epic.getEpicId())

        then:
        epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)

        deletedEpic.getEpicId() == epic.getEpicId()
        deletedEpic.getName() == epic.getName()
    }

    def "deleteEpicById should throw exception when the entity is not saved in the database"() {
        given:
        Epic epic = new Epic()
        epic.setEpicId(UUID.randomUUID())
        epic.setName("Random name")

        epicsRepository.findByEpicId(_) >> Optional.empty()
        when:
        epicsService.delete(epic.getEpicId())
        then:
        thrown(IllegalArgumentException)
    }


    def "findById method should return the epic entity by the passed epiId argument and the entity is in the db"() {
        given:
        Epic epic = new Epic()
        epic.setEpicId(UUID.randomUUID())
        epic.setName("random name")

        epicsRepository.save(epic)

        when:
        def foundEpic = epicsService.find(epic.getEpicId())
        then:
        1 * epicsRepository.existsById(epic.getEpicId()) >> Optional.of(epic)
        1 * epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)

        foundEpic.getEpicId() == epic.getEpicId()
        foundEpic.getName() == epic.getName()
    }

    def "FindById method should throw Exception when no entity is saved in db"() {
        given:
        Epic epic = new Epic()
        epic.setEpicId(UUID.randomUUID())
        epic.setName("Random name")

        when:
        epicsService.find(epic.getEpicId())
        then:
        thrown(IllegalArgumentException)
    }

    def "UpdateById should update the entity if saved in the database"() {
        given:
        Epic epic = new Epic()
        epic.setEpicId(UUID.randomUUID())
        epic.setName("Random name")


        epicsRepository.save(epic)

        Epic updatedEpic = new Epic()
        updatedEpic.setEpicId(epic.getEpicId())
        updatedEpic.setName("Updated name")

        epicsRepository.save(epic)

        when:
        def epicToBeUpdated = epicsService.update(epic.getEpicId(), updatedEpic)

        then:
        1 * epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)
        epicToBeUpdated.getEpicId() == epic.getEpicId()
        epicToBeUpdated.getName() == epic.getName()
    }

    def "UpdateById should throw exception when no entity is saved in the database"() {
        given:
        Epic epic = new Epic()
        epic.setEpicId(UUID.randomUUID())
        epic.setName("Random name")

        Epic updatedEpic = new Epic()
        updatedEpic.setEpicId(epic.getEpicId())
        updatedEpic.setName("Updated name")

        epicsRepository.findByEpicId(_) >> Optional.empty()

        when:
        epicsService.update(epic.getEpicId(), updatedEpic)
        then:
        thrown(IllegalArgumentException)
    }

    def "Test updateEpic() if entity field is correctly uprated as expected"() {
        given:
        Epic epic = new Epic()
        epic.setEpicId(UUID.randomUUID())
        epic.setName("Random name")

        Epic updatedEpic = new Epic()
        updatedEpic.setEpicId(epic.getEpicId())
        updatedEpic.setName("Updated name")

        epicsRepository.save(epic)

        when:
        def resultEpic = epicsService.update(epic.getEpicId(), updatedEpic)

        then:
        1 * epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)

        resultEpic.getEpicId() == epic.getEpicId()
        resultEpic.getName() == epic.getName()

    }

    def "Test if updateEpic() will throw and exception if epic is not present"() {
        given:
        Epic epic = new Epic()
        epic.setEpicId(UUID.randomUUID())
        epic.setName("Random name")

        epicsRepository.findByEpicId(_) >> Optional.empty()
        when:
        epicsService.update(epic.getEpicId(), new Epic(epicId: UUID.randomUUID(), name: "Update name"))
        then:
        thrown(IllegalArgumentException)
    }

    def "Test if patch() method will update an epic entity field when epic is present in database"() {
        given:
        Epic epic = new Epic()
        epic.setEpicId(UUID.randomUUID())
        epic.setName("Random name")

        Map<Object, Object> fields = [epicId: epic.getEpicId(),
                                      name  : "Patched name"]

        epicsRepository.save(epic)

        when:
        def patchedEpic = epicsService.patch(epic.getEpicId(), fields)

        then:
        epicsRepository.findByEpicId(epic.getEpicId()) >> Optional.of(epic)

        patchedEpic.getEpicId() == epic.getEpicId()
        patchedEpic.getName() == epic.getName()

    }

    def "Test if patch() will throw an exception if epic is not present"() {
        given:
        Epic epic = new Epic()
        epic.setEpicId(UUID.randomUUID())
        epic.setName("Random name")

        Map<Object, Object> fields = [epicId: epic.getEpicId(),
                                      name  : "Patched name"]

        epicsRepository.findByEpicId(_) >> Optional.empty()
        when:
        epicsService.patch(epic.getEpicId(), fields);
        then:
        thrown(IllegalArgumentException)

    }

}
