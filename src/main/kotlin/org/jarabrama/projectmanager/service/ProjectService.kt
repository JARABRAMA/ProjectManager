package org.jarabrama.projectmanager.service

import org.jarabrama.projectmanager.model.entity.Project
import java.time.LocalDate

interface ProjectService {
    fun findAll(): List<Project>
    fun find(id: Long): Project
    fun find(name: String): List<Project>
    fun save(name: String, description: String, startDate: LocalDate? = LocalDate.now(), endDate: LocalDate)
    fun update(id: Long, name: String, description: String, startDate: LocalDate?, endDate: LocalDate?)
    fun delete(id: Long)
}