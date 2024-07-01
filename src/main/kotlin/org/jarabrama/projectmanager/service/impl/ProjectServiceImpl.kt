package org.jarabrama.projectmanager.service.impl

import org.jarabrama.projectmanager.exception.project.ProjectAlreadyExistException
import org.jarabrama.projectmanager.exception.project.ProjectNotFoundException
import org.jarabrama.projectmanager.model.entity.Project
import org.jarabrama.projectmanager.repository.ProjectRepository
import org.jarabrama.projectmanager.service.ProjectService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ProjectServiceImpl(private val projectRepository: ProjectRepository) : ProjectService {

    override fun findAll(): List<Project> {
        return projectRepository.findAll()
    }

    override fun find(id: Long): Project {
        val optProject = projectRepository.findById(id)
        if (optProject.isEmpty) {
            throw ProjectNotFoundException(id)
        }
        return optProject.get()
    }

    override fun find(name: String): Project {
        val optProject = projectRepository.findByName(name)
        if (optProject.isEmpty) {
            throw ProjectNotFoundException(name)
        }
        return optProject.get()
    }

    override fun save(name: String, description: String, startDate: LocalDate?, endDate: LocalDate) {
        try {
            this.find(name) // it'll throw a ProjectNotFoundException if the project does not exist
            throw ProjectAlreadyExistException(name)
        } catch (e: ProjectNotFoundException) {
            projectRepository.save(
                Project(
                    name = name,
                    description = description,
                    startDate = startDate ?: LocalDate.now(),
                    endDate = endDate,
                )
            )
        }
    }

    override fun update(id: Long, name: String, description: String, startDate: LocalDate?, endDate: LocalDate?) {
        val project = this.find(id) // if project does not exist it'll throw a ProjectNotFoundException
        projectRepository.update(id, name, description, startDate ?: project.startDate, endDate ?: project.endDate)
    }

    override fun delete(id: Long) {
        val project = this.find(id) // if project does not exist it'll throw a ProjectNotFoundException
        projectRepository.delete(project)
    }
}