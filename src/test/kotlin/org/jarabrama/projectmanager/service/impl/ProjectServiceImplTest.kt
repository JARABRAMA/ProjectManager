package org.jarabrama.projectmanager.service.impl

import org.jarabrama.projectmanager.exception.project.ProjectAlreadyExistException
import org.jarabrama.projectmanager.exception.project.ProjectNotFoundException
import org.jarabrama.projectmanager.model.entity.Project
import org.jarabrama.projectmanager.service.ProjectService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.*

@SpringBootTest
class ProjectServiceImplTest {
    @Autowired
    private lateinit var projectService: ProjectService

    private val projects = listOf(
        Project(
            name = "project manager",
            description = "project manager description",
            endDate = LocalDate.of(2024, 7, 3)
        ),
        Project(
            name = "phone app",
            description = "phone app description",
            endDate = LocalDate.of(2024, 7, 4)
        ),
        Project(
            name = "desktop app",
            description = "desktop app description",
            endDate = LocalDate.of(2024, 7, 5)
        ),
        Project(
            name = "ios app",
            description = "ios app description",
            endDate = LocalDate.of(2024, 7, 6)
        ),
        Project(
            name = "flutter app",
            description = "flutter app description",
            endDate = LocalDate.of(2024, 7, 8)
        )
    )

    private fun setUp() {
        projects.forEach {
            projectService.save(
                name = it.name,
                description = it.description,
                startDate = it.startDate,
                endDate = it.endDate
            )
        }
    }

    private fun clearAll() {
        projectService.findAll().forEach {
            val project = projectService.find(it.name)
            projectService.delete(project.id)
        }
    }

    @Test
    fun `founding five projects`() {
        clearAll()
        setUp()
        val foundedProjects = projectService.findAll()
        println(projects)
        println(foundedProjects)
        assertTrue(compareTwoListOfProjects(this.projects, foundedProjects))
    }

    @Test
    fun `there is not any project`() {
        clearAll()
        assertTrue(projectService.findAll().isEmpty())
    }

    @Test
    fun `deleting a project that not exist`() {
        clearAll()
        setUp()
        val removedProject = projectService.find("ios app")
        projectService.delete(removedProject.id)

        assertThrows<ProjectNotFoundException> {
            projectService.delete(removedProject.id)
        }
    }

    @Test
    fun `changing the description of the project manager`() {
        clearAll()
        setUp()
        val expected = projectService.find("project manager")
        expected.name = "The amazing project manager"
        expected.description = "this is the new description of the ${expected.name}"
        projectService.update(
            expected.id,
            expected.name,
            expected.description,
            expected.startDate,
            expected.endDate
        )
        val founded = projectService.find(expected.id)
        println(projectService.findAll().size)
        println(founded)
        println(expected)
        assertTrue(compareTwoProjects(expected, founded))
    }

    @Test
    fun `removing the flutter app`() {
        clearAll()
        setUp()
        val expected: MutableList<Project> = projects.toMutableList()
        expected.removeAt(4)
        val removedProject = projectService.find("flutter app")
        projectService.delete(removedProject.id)
        val projectList = projectService.findAll()
        assertTrue(compareTwoListOfProjects(expected, projectList))
    }

    @Test
    fun `tying to get a non-exist project`() {
        clearAll()
        setUp()
        assertThrows<ProjectNotFoundException> {
            projectService.find("non-exist project")
        }
    }

    @Test
    fun `put in a project that already exist`() {
        clearAll()
        setUp()
        assertThrows<ProjectAlreadyExistException> {
            projectService.save(
                name = "flutter app",
                description = "flutter app description",
                endDate = LocalDate.of(2024, 7, 8)
            )
        }
    }

    private fun compareTwoListOfProjects(w: List<Project>, v: List<Project>): Boolean {
        var isEquals = false
        w.forEachIndexed { i, project -> isEquals = compareTwoProjects(project, v[i]) }
        return isEquals
    }

    private fun compareTwoProjects(x: Project, y: Project): Boolean {
        return x.name == y.name && x.description == y.description &&
                Objects.equals(x.startDate, y.startDate) && Objects.equals(x.endDate, y.endDate)
    }
}
