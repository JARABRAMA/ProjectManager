package org.jarabrama.projectmanager.service.impl

import org.jarabrama.projectmanager.exception.developer.EmailNotFoundException
import org.jarabrama.projectmanager.model.entity.Developer
import org.jarabrama.projectmanager.model.enums.Role
import org.jarabrama.projectmanager.service.DeveloperService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DeveloperServiceImplTest {
    @Autowired
    private lateinit var developerService: DeveloperService

    private val developers = listOf(
        Developer(fullName = "Jon Doe", email = "jondoe@example.com", password = "password", role = Role.DEVELOPER),
        Developer(fullName = "Juan", email = "juan@example.com", password = "password", role = Role.ADMIN),
        Developer(fullName = "Mariana", email = "mariana@example.com", password = "password", role = Role.DEVELOPER),
        Developer(fullName = "Diego", email = "diego@example.com", password = "password", role = Role.DEVELOPER),
        Developer(fullName = "Juliane", email = "juliane@example.com", password = "password", role = Role.DEVELOPER)
    )

    private fun removeAll() {
        val developers = developerService.findAll()
        developers.forEach {
            it.id?.let { id -> developerService.remove(id) }
        }
    }

    private fun setUp() {
        developers.forEach { developer ->
            developerService.save(developer.email, developer.password, developer.fullName, developer.role)
        }
    }

    @Test
    fun `finding user by email`() {
        removeAll()
        setUp()
        val expected =
            Developer(fullName = "Diego", email = "diego@example.com", password = "password", role = Role.DEVELOPER)
        val founded = developerService.find("diego@example.com")
        assertTrue(compareDevelopers(expected, founded))
    }

    @Test
    fun `error searching a non-existing email`() {
        removeAll()
        setUp()
        assertThrows<EmailNotFoundException> {
            developerService.find("nonexistendemail@example.com")
        }

    }

    @Test
    fun `Juliane was promoted and her password was changed`() {
        removeAll()
        setUp()
        val newJuliane = developerService.find("juliane@example.com")
        newJuliane.password = "sa"
        newJuliane.role = Role.ADMIN
        newJuliane.id?.let { developerService.update(it, newJuliane.email, newJuliane.password, newJuliane.fullName, newJuliane.role) }
        val foundedJuliane = developerService.find("juliane@example.com")
        assertTrue(compareDevelopers(newJuliane, foundedJuliane))


    }

    @Test
    fun `Mariana developer was removed`() {
        removeAll()
        setUp()
        val mariana = developerService.find("mariana@example.com")// there is just one Mariana
        val expected = listOf(
            Developer(fullName = "Jon Doe", email = "jondoe@example.com", password = "password", role = Role.DEVELOPER),
            Developer(fullName = "Juan", email = "juan@example.com", password = "password", role = Role.ADMIN),
            Developer(fullName = "Diego", email = "diego@example.com", password = "password", role = Role.DEVELOPER),
            Developer(fullName = "Juliane", email = "juliane@example.com", password = "password", role = Role.DEVELOPER)
        )
        mariana.id.let { developerService.remove(it!!) }
        val listDevelopers = developerService.findAll()
        println(listDevelopers)
        println(expected)
        assertTrue(compareTwoList(expected, listDevelopers))
    }

    @Test
    fun `findAll of 5 developers`() {
        removeAll()
        setUp()
        val foundedDevelopers = developerService.findAll()
        compareTwoList(foundedDevelopers, developers)
    }

    @Test
    fun `finding Jon Doe`() {
        removeAll()
        val expected = listOf(
            Developer(fullName = "Jon Doe", email = "jondoe@example.com", password = "password", role = Role.DEVELOPER)
        )
        setUp()
        val founded = developerService.findAllByName("Jon Doe")
        assertTrue(compareTwoList(expected, founded))
    }

    @Test
    fun `dont find any user`() {
        removeAll()
        val users = developerService.findAll()
        assertTrue(users.isEmpty())
    }

    @Test
    fun `adding a new developer`() {
        removeAll()
        val developer =
            Developer(fullName = "Jon Doe", email = "jondoe@example.com", password = "password", role = Role.DEVELOPER)
        val savedDeveloper =
            developerService.save(developer.email, developer.password, developer.fullName, developer.role)
        assertTrue(compareDevelopers(developer, savedDeveloper))
    }

    private fun compareTwoList(v: List<Developer>, w: List<Developer>): Boolean {
        var isEquals = false
        v.forEachIndexed { index, developer ->
            isEquals = compareDevelopers(developer, w[index])
        }
        return isEquals
    }

    private fun compareDevelopers(x: Developer, y: Developer): Boolean {
        return x.fullName == y.fullName &&
                x.email == y.email &&
                x.password == y.password &&
                x.role == y.role
    }
}