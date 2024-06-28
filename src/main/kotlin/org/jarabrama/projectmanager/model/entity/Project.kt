package org.jarabrama.projectmanager.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
class Project (
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    val id: Long = 0,
    @Column
    var name: String,
    @Column
    var description: String,
    @Column
    val startDate: LocalDate,
    @Column
    var endDate: LocalDate
)