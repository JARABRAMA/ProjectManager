package org.jarabrama.projectmanager.service.impl

import org.jarabrama.projectmanager.exception.task.TaskNotFoundException
import org.jarabrama.projectmanager.model.entity.Task
import org.jarabrama.projectmanager.service.TaskService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import org.jarabrama.projectmanager.model.enums.Status

@SpringBootTest
class TaskServiceImplTest {
    @Autowired
    private lateinit var taskService: TaskService

    private val tasks = listOf(
        Task(
            title = "Test the service",
            description = "test service description",
            developerId = 0,
            projectId = 0,
            finishDate = LocalDate.of(2024, 12, 1)
        ),
        Task(
            title = "Test the controller",
            description = "test service controller",
            developerId = 0,
            projectId = 0,
            finishDate = LocalDate.of(2024, 12, 2)
        ),
        Task(
            title = "create web consumer",
            description = "create web consumer description",
            developerId = 0,
            projectId = 0,
            finishDate = LocalDate.of(2024, 10, 1)
        ),
        Task(
            title = "create mobile consumer",
            description = "mobile consumer description",
            developerId = 0,
            projectId = 0,
            finishDate = LocalDate.of(2024, 11, 1)
        )
    )

    fun startUp() {
        tasks.forEach { taskService.save(it) }
    }

    fun clearAll() {
        taskService.findAll().forEach { task -> task.id?.let { taskService.remove(it) } }
    }

    @Test
    fun `save five entities`() {
        clearAll()
        startUp()
        assertTrue(compareTwoList(tasks, taskService.findAll()))
    }

    @Test
    fun `set testing service task as done`() {
        clearAll()
        startUp()
        val expected = taskService.findAll()[0]
        expected.status = Status.DONE

        expected.id?.let { taskService.updateStatus(it, Status.DONE) }

        assertTrue(compareTwoTask(expected, taskService.find(expected.id ?: 12312)))
    }

    @Test
    fun `deleting testing service task`() {
        clearAll()
        startUp()
        val expected = tasks.toMutableList()
        expected.removeAt(0)

        val deletedTask = taskService.findAll()[0]
        deletedTask.id?.let { taskService.remove(it) }

        assertTrue(compareTwoList(expected, taskService.findAll()))
    }

    @Test
    fun `updating description of the testing service task`() {
        clearAll()
        startUp()
        val expected = taskService.findAll()[0]
        expected.description = "a new description"

        expected.id?.let {
            taskService.update(
                taskId = it,
                description = expected.description,
                title = expected.title,
                developerId = expected.developerId,
                projectId = expected.projectId,
                initDate = expected.initDate,
                finishDate = expected.finishDate
            )
        }
        assertTrue(compareTwoTask(expected, taskService.find(expected.id?: 2341234)))


    }

    @Test
    fun `finding a non existing task`() {
        clearAll()
        assertThrows<TaskNotFoundException> { taskService.find(0L) }
    }

    @Test
    fun `deleting a non existing task`() {
        clearAll()
        assertThrows<TaskNotFoundException> { taskService.remove(0L) }
    }

    @Test
    fun `finding a task`() {
        clearAll()
        startUp()
        val expected = taskService.findAll()[0]

        val foundedTask =
            expected.id?.let { taskService.find(it) } ?: Task(0L, "", "", 0L, 0L, finishDate = LocalDate.now())

        assertTrue(compareTwoTask(expected, foundedTask))
    }

    private fun compareTwoList(w: List<Task>, v: List<Task>): Boolean {
        var isEquals = false
        w.forEachIndexed { index, task -> isEquals = compareTwoTask(task, v[index]) }
        return isEquals
    }

    private fun compareTwoTask(x: Task, y: Task): Boolean {
        return x.title == y.title && x.description == y.description &&
                x.initDate == y.initDate && x.finishDate == y.finishDate &&
                x.developerId == y.developerId && x.projectId == y.projectId &&
                x.status == y.status
    }
}