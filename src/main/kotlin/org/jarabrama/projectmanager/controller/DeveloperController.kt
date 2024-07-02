package org.jarabrama.projectmanager.controller

import org.apache.logging.log4j.LogManager
import org.jarabrama.projectmanager.exception.developer.DeveloperAlreadyExistException
import org.jarabrama.projectmanager.exception.developer.DeveloperNotFoundException
import org.jarabrama.projectmanager.model.entity.Developer
import org.jarabrama.projectmanager.service.DeveloperService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.jarabrama.projectmanager.model.enums.Role
import org.springframework.http.RequestEntity

@Controller
class DeveloperController(private val developerService: DeveloperService) {
    private val log = LogManager.getLogger()

    @GetMapping("/developers")
    fun findAll(): ResponseEntity<List<Developer>> {
        return ResponseEntity.ok(developerService.findAll())
    }

    @GetMapping("/developers/{name}")
    fun findAllByName(@PathVariable name: String): ResponseEntity<List<Developer>> {
        return ResponseEntity.ok(developerService.findAllByName(name))
    }

    @GetMapping("/developer-id/{id}")
    fun find(@PathVariable id: Long): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(developerService.find(id))
        } catch (e: DeveloperNotFoundException) {
            log.error(e.message)
            val errorMessage = mapOf("error" to e.message)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage)
        }
    }

    @GetMapping("/developer-email/{email}")
    fun find(@PathVariable email: String): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(developerService.find(email))
        } catch (e: DeveloperNotFoundException) {
            log.error(e.message)
            val errorMessage = mapOf("error" to e.message)
            return ResponseEntity.badRequest().body(errorMessage)
        }
    }

    @DeleteMapping("/developer/")
    fun delete(@RequestParam id: Long): ResponseEntity<Map<String, String>> {
        try {
            developerService.remove(id)
            val message = mapOf("success" to "developer $id was removed")
            return ResponseEntity.ok(message)
        } catch (e: DeveloperNotFoundException) {
            log.error(e.message)
            val errorMessage = mapOf("error" to (e.message ?: "can't delete developer id: $id"))
            return ResponseEntity.badRequest().body(errorMessage)
        }
    }

    @PostMapping("/developer")
    fun save(@RequestBody developer: Developer): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(developerService.save(developer))
        } catch (e: DeveloperAlreadyExistException) {
            log.error(e.message)
            val errorMessage = mapOf("error" to e.message)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage)
        }
    }

    @PostMapping("/developer/update")
    fun update(
        @RequestParam id: Long,
        @RequestParam email: String,
        @RequestParam password: String,
        @RequestParam fullName: String,
        @RequestParam role: Role
    ): ResponseEntity<Map<String, String>> {
        try {
            developerService.update(id, email, password, fullName, role)
            val message = mapOf("success" to "developer $fullName was updated")
            return ResponseEntity.ok(message)
        } catch (e: DeveloperNotFoundException) {
            val errorMessage = mapOf("error" to "developer id $id was not found")
            return ResponseEntity.badRequest().body(errorMessage)
        }
    }
}