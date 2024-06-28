package org.jarabrama.projectmanager.service

import org.jarabrama.projectmanager.model.entity.Developer
import org.jarabrama.projectmanager.model.enums.Role


interface DeveloperService {
    fun findAll(): List<Developer>
    fun findAllByName(fullName: String): List<Developer>
    fun find(id: Long): Developer
    fun remove(id: Long)
    fun save(email: String, password: String, fullName: String, role: Role)
    fun update(id: Long, email: String, password: String, fullName: String, role: Role)
}