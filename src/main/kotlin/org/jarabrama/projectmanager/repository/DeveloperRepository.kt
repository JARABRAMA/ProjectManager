package org.jarabrama.projectmanager.repository

import org.jarabrama.projectmanager.model.entity.Developer
import org.springframework.data.jpa.repository.JpaRepository

interface DeveloperRepository: JpaRepository<Developer, Long> {

}