package org.jarabrama.projectmanager.service


import org.jarabrama.projectmanager.model.entity.Task
import org.jarabrama.projectmanager.model.enums.Status
import java.time.LocalDate

interface TaskService {
    fun findAll(): List<Task>
    fun find(id: Long): Task
    fun findAllByDeveloper(userId: Long): List<Task>
    fun findAllByProject(projectId: Long): List<Task>
    fun updateStatus(id: Long, status: Status)
    fun update(
        taskId: Long,
        developerId: Long?,
        projectId: Long?,
        title: String?,
        description: String?,
        initDate: LocalDate?,
        finishDate: LocalDate?
    )

    fun remove(taskId: Long)
    fun save(task: Task): Task
}