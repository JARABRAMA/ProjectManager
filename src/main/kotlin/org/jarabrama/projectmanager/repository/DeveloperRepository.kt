package org.jarabrama.projectmanager.repository

import jakarta.transaction.Transactional
import org.jarabrama.projectmanager.model.entity.Developer
import org.jarabrama.projectmanager.model.enums.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface DeveloperRepository: JpaRepository<Developer, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Developer d SET d.fullName = :fullName, d.email = :email, d.password = :password, d.role = :role WHERE d.id = :id")
    fun update(id: Long, fullName: String, email: String, password: String, role: Role)

    fun findByEmail(email: String): Optional<Developer>
}