package org.jarabrama.projectmanager.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.jarabrama.projectmanager.model.entity.Project

interface ProjectRepository: JpaRepository<Project, Long> {
}