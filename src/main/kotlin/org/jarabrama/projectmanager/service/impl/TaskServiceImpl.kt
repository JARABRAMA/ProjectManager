package org.jarabrama.projectmanager.service.impl

import org.jarabrama.projectmanager.exception.task.TaskNotFoundException
import org.jarabrama.projectmanager.model.entity.Task
import org.jarabrama.projectmanager.model.enums.Status
import org.jarabrama.projectmanager.repository.TaskRepository
import org.jarabrama.projectmanager.service.TaskService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TaskServiceImpl(private val taskRepository: TaskRepository) : TaskService {

    override fun findAll(): List<Task> {
        return taskRepository.findAll()
    }

    override fun findAllByDeveloper(userId: Long): List<Task> {
        return taskRepository.findAllByDeveloperId(userId)
    }

    override fun findAllByProject(projectId: Long): List<Task> {
        return taskRepository.findAllByProjectId(projectId)
    }

    override fun save(task: Task): Task {
        return taskRepository.save(task)
    }

    override fun find(id: Long): Task {
        val optTask = taskRepository.findById(id)
        if (optTask.isEmpty) {
            throw TaskNotFoundException(id)
        }
        return optTask.get()
    }

    override fun updateStatus(id: Long, status: Status) {
        find(id) // if the task does not exit it'll throw a TaskNotFoundException
        taskRepository.updateStatus(id, status)
    }

    override fun update(
        taskId: Long,
        developerId: Long?,
        projectId: Long?,
        title: String?,
        description: String?,
        initDate: LocalDate?,
        finishDate: LocalDate?
    ) {
        val task = find(taskId) // if the task does not exit it'll throw a TaskNotFoundException
        (initDate ?: task.initDate)?.let {
            taskRepository.update(
                taskId,
                developerId ?: task.developerId,
                projectId ?: task.projectId,
                title ?: task.title,
                description ?: task.description,
                it,
                finishDate ?: task.finishDate
            )
        }
    }

    override fun remove(taskId: Long) {
        val task = find(taskId) // if the task does not exit it'll throw a TaskNotFoundException
        taskRepository.delete(task)
    }
}