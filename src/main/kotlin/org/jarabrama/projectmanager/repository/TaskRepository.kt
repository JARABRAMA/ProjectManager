package org.jarabrama.projectmanager.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.scheduling.config.Task

interface TaskRepository: JpaRepository<Task, Long> {
}