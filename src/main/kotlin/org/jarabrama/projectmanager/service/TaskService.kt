package org.jarabrama.projectmanager.service


import org.jarabrama.projectmanager.model.entity.Task
import org.jarabrama.projectmanager.model.enums.Status
import java.time.LocalDate

interface TaskService {
    fun findAll(): List<Task>
    fun findAllByUser(userId: Long): List<Task>
    fun findAllByProject(projectId: Long): List<Task>
    fun save(projectId: Long, userId: Long, title: String, description: String, status: Status? = Status.TODO, date: LocalDate? = LocalDate.now())
    fun updateStatus(id: Long, status: Status)
    fun update(taskId: Long, userId: Long, title: String, description: String)
    fun remove(taskId: Long)
}