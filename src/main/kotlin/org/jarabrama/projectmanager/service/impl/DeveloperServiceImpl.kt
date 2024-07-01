package org.jarabrama.projectmanager.service.impl

import org.jarabrama.projectmanager.exception.developer.DeveloperAlreadyExistException
import org.jarabrama.projectmanager.exception.developer.DeveloperNotFoundException
import org.jarabrama.projectmanager.exception.developer.EmailNotFoundException
import org.jarabrama.projectmanager.model.entity.Developer
import org.jarabrama.projectmanager.model.enums.Role
import org.jarabrama.projectmanager.service.DeveloperService
import org.springframework.stereotype.Service
import org.jarabrama.projectmanager.repository.DeveloperRepository
import org.slf4j.LoggerFactory
import org.slf4j.Marker


@Service
class DeveloperServiceImpl(private val developerRepository: DeveloperRepository) : DeveloperService {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun findAll(): List<Developer> {
        return developerRepository.findAll()
    }

    override fun findAllByName(fullName: String): List<Developer> {
        return findAll().filter { it.fullName == fullName }
    }

    override fun find(id: Long): Developer {
        val optDeveloper = developerRepository.findById(id)
        if (optDeveloper.isEmpty) {
            log.error("DeveloperService: error founding developer $id")
            throw DeveloperNotFoundException(id)
        }
        return optDeveloper.get()
    }

    override fun remove(id: Long) {
        val developer = this.find(id)
        developerRepository.delete(developer)
    }

    override fun find(email: String): Developer {
        val optDeveloper = developerRepository.findByEmail(email)
        if (optDeveloper.isEmpty) {
            throw EmailNotFoundException(email)
        }
        return optDeveloper.get()
    }

    override fun save(email: String, password: String, fullName: String, role: Role): Developer {
        val exist = findAll().firstOrNull {it.email == email}
        if (exist == null) {
            return developerRepository.save(
                Developer(
                    email = email,
                    password = password,
                    fullName = fullName,
                    role = role
                )
            )
        }
        throw DeveloperAlreadyExistException(email)
    }

    override fun update(id: Long, email: String, password: String, fullName: String, role: Role) {
        this.find(id) // if the developer does not exist it'll trow a DeveloperNotFoundException
        developerRepository.update(id, fullName, email, password, role)
    }
}