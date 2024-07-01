package org.jarabrama.projectmanager.repository

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.jarabrama.projectmanager.model.entity.Task
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.jarabrama.projectmanager.model.enums.Status
import java.time.LocalDate

interface TaskRepository: JpaRepository<Task, Long> {
    fun findAllByDeveloperId(developerId: Long): List<Task>
    fun findAllByProjectId(projectId: Long): List<Task>

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.status = :status WHERE t.id = :id")
    fun updateStatus(id: Long, status: Status)

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.developerId = :developerId, t.projectId = :projectId, t.title = :title, t.description = :description, t.initDate = :initDate, t.finishDate = :finishDate WHERE t.id = :taskId")
    fun update(taskId: Long, developerId: Long, projectId: Long, title: String, description: String, initDate: LocalDate, finishDate: LocalDate)
}