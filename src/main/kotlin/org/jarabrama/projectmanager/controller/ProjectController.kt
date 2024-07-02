package org.jarabrama.projectmanager.controller

import org.jarabrama.projectmanager.exception.project.ProjectAlreadyExistException
import org.jarabrama.projectmanager.exception.project.ProjectNotFoundException
import org.jarabrama.projectmanager.model.entity.Project
import org.jarabrama.projectmanager.service.ProjectService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate

@Controller
class ProjectController(private val projectService: ProjectService) {

    @GetMapping("/projects")
    fun findAll(): ResponseEntity<List<Project>> {
        return ResponseEntity.ok(projectService.findAll())
    }

    @GetMapping("/project/id")
    fun find(@RequestParam id: Long): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(projectService.find(id))
        } catch (e: ProjectNotFoundException) {
            val errorMessage = mapOf("error" to e.message)
            return ResponseEntity.badRequest().body(errorMessage)
        }
    }

    @GetMapping("/project/name")
    fun find(@RequestParam name: String): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(projectService.find(name))
        } catch (e: ProjectNotFoundException) {
            val errorMessage = mapOf("error" to e.message)
            return ResponseEntity.badRequest().body(errorMessage)
        }
    }

    @PostMapping("/project")
    fun save(@RequestBody project: Project): ResponseEntity<Map<String, String>> {
        try {
            projectService.save(project)
            return ResponseEntity.ok(mapOf("success" to "Project ${project.name} was saved"))
        } catch (e: ProjectAlreadyExistException) {
            e.message?.let {
                val errorMessage = mapOf("error" to it)
                return@save ResponseEntity.badRequest().body(errorMessage)
            }
        }
        return ResponseEntity.badRequest().body(mapOf("error" to "unexpected error"))
    }

    @PostMapping("/project/update")
    fun update(
        @RequestParam id: Long,
        @RequestParam name: String,
        @RequestParam description: String,
        @RequestParam startDate: LocalDate?,
        @RequestParam endDate: LocalDate?
    ): ResponseEntity<Map<String, String>> {
        try {
            projectService.update(id, name, description, startDate, endDate)
            val message = mapOf("success" to "Project $name was saved")
            return ResponseEntity.ok(message)
        } catch (e: ProjectNotFoundException) {
            e.message?.let {
                val errorMessage = mapOf("error" to it)
                return@update ResponseEntity.badRequest().body(errorMessage)
            }
        }
        return ResponseEntity.badRequest().body(mapOf("error" to "Unexpected error"))
    }


    @DeleteMapping("/project")
    fun delete(@RequestParam id: Long): ResponseEntity<Map<String, String>> {
        try {
            projectService.delete(id)
            return ResponseEntity.ok(mapOf("success" to "project $id was deleted"))
        } catch (e: ProjectNotFoundException) {
            e.message?.let {
                val errorMessage = mapOf("error" to it)
                return@delete ResponseEntity.badRequest().body(errorMessage)
            }
        }
        return ResponseEntity.badRequest().body(mapOf("error" to "Unexpected error"))
    }
}