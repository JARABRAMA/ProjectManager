package org.jarabrama.projectmanager.repository

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.jarabrama.projectmanager.model.entity.Project
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

interface ProjectRepository: JpaRepository<Project, Long> {
    fun findByName(name: String): Optional<Project>

    @Transactional
    @Modifying
    @Query("UPDATE Project p SET p.name = :name, p.description = :description, p.startDate = :startDate, p.endDate = :endDate WHERE p.id = :id")
    fun update(id: Long, name: String, description: String, startDate: LocalDate? = LocalDate.now(), endDate: LocalDate)
}