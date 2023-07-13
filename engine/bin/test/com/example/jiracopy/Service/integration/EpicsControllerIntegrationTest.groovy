package com.example.jiracopy.Service.integration

import com.example.jiracopy.Controller.EpicsController
import com.example.jiracopy.Entity.Epic.Epic
import com.example.jiracopy.Repository.EpicsRepository
import com.example.jiracopy.Service.EpicsServices.EpicsService
import org.springframework.beans.factory.annotation.Autowired

class EpicsControllerIntegrationTest extends IntegrationTest {
    @Autowired
    EpicsService epicsService

    @Autowired
    EpicsRepository epicsRepository

    @Autowired
    EpicsController epicsController;



    def cleanup() {
        epicsRepository.deleteAll()
    }


    def "Test if saving epic in the database is successful"() {
        given:
        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Test epic", assignee: "labaduba@abv.bg")

        def sizeOfDatabaseBeforeSavingTheEntity = epicsRepository.findAll().size()

        when:
        epicsController.newEpic(epic)

        then:
        def latestEpicIndex = epicsRepository.findAll().findLastIndexOf { epic }
        def latestEpic = epicsRepository.findAll()[latestEpicIndex]
        latestEpic.getName() == epic.getName()
        epicsRepository.findAll().size() == sizeOfDatabaseBeforeSavingTheEntity + 1
    }


    def "Test one() if a saved epic is returned when passed it's id as an argument"() {
        given:

        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random name", assignee: "labaduba@abv.bg")

        epicsController.newEpic(epic)

        def latestEpicIndex = epicsRepository.findAll().findLastIndexOf { epic }
        def lastEpicInDatabase = epicsRepository.findAll()[latestEpicIndex]

        when:
        epicsController.one(lastEpicInDatabase.getEpicId())

        then:
        def expectedEpic = new Epic(epicId: lastEpicInDatabase.getEpicId(), name: lastEpicInDatabase.getName(), assignee: "Unknown")
        expectedEpic.getName() == lastEpicInDatabase.getName()


    }

    def "Test one() if the epic is not saved in the database"() {

        when:
        epicsController.one(UUID.randomUUID())
        then:
        thrown(IllegalArgumentException)
    }

    def "Test updateEpic() if the epic is saved in the database & then successfully updated"() {
        given:
        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random name", assignee: "labaduba@abv.bg")

        epicsController.newEpic(epic)

        def listOfEpics = epicsRepository.findAll()
        def lastEpic = listOfEpics.get(listOfEpics.size() - 1)

        when:

        epicsController.updateEpic(lastEpic.getEpicId(), new Epic(epicId: lastEpic.getEpicId(), name: "Some updated name", assignee: "Unknown"))

        then:
        def expectedEpic = epicsRepository.findByEpicId(lastEpic.getEpicId()).get()
        expectedEpic.getEpicId() == lastEpic.getEpicId()
        expectedEpic.getName() == "Some updated name"

    }

    def "Test updateEpic() if entity is not present"() {
        when:
        epicsController.updateEpic(UUID.randomUUID(), new Epic(epicId: UUID.randomUUID(), name: "New name", assignee: "Unknown"))
        then:
        thrown(IllegalArgumentException)

    }

    def "Test deleteEpic() when the entity is present in the database"() {
        given:
        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random epic", assignee: "labaduba@abv.bg")

        epicsController.newEpic(epic)

        def listOfEpics = epicsRepository.findAll()
        def lastEpic = listOfEpics.get(listOfEpics.size() - 1)
        def sizeOfDatabaseBeforeSavingTheEntity = epicsRepository.findAll().size()

        when:
        epicsController.deleteEpic(lastEpic.getEpicId())

        then:
        epicsRepository.findAll().size() == sizeOfDatabaseBeforeSavingTheEntity - 1
        epicsRepository.findByEpicId(lastEpic.getEpicId()) == Optional.empty()


    }

    def "Test deleteEpic() when the entity is not present in the database"() {
        when:
        epicsController.deleteEpic(UUID.randomUUID())
        then:
        thrown(IllegalArgumentException)
    }

    def "Test patchEpic() when the entity is present in the database"() {
        given:

        Epic epic = new Epic(epicId: UUID.randomUUID(), name: "Random name", assignee: "labaduba@abv.bg")
        epicsController.newEpic(epic)
        def listOfEpics = epicsRepository.findAll()
        def lastEpic = listOfEpics.get(listOfEpics.size() - 1)
        Map<Object, Object> fields = [epicId: lastEpic.getEpicId(),
                                      name  : "Some patched name"]

        when:
        epicsController.patchEpic(lastEpic.getEpicId(), fields)
        then:
        def expectedEpic = epicsRepository.findByEpicId(lastEpic.getEpicId())
        expectedEpic.get().getEpicId() == lastEpic.getEpicId()
        expectedEpic.get().getName() == "Some patched name"

    }

    def "Test if patchEpic() will throw an exception if the epic is not present in the database"() {
        when:
        epicsController.patchEpic(UUID.randomUUID(), [epicId: UUID.randomUUID(),
                                                      name  : "Random name", assignee: "Unknown"])
        then:
        thrown(IllegalArgumentException)
    }


}
