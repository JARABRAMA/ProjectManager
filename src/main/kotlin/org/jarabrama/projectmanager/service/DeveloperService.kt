package org.jarabrama.projectmanager.service

import org.jarabrama.projectmanager.model.entity.Developer
import org.jarabrama.projectmanager.model.enums.Role
import org.springframework.stereotype.Repository


interface DeveloperService {
    fun findAll(): List<Developer>
    fun findAllByName(fullName: String): List<Developer>
    fun find(id: Long): Developer
    fun find(email: String): Developer
    fun remove(id: Long)
    fun save(email: String, password: String, fullName: String, role: Role): Developer
    fun update(id: Long, email: String, password: String, fullName: String, role: Role)
}