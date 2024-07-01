package org.jarabrama.projectmanager.model.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table
class Project (
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    val id: Long = 0,
    @Column(unique = true)
    var name: String,
    @Column
    var description: String,
    @Column
    val startDate: LocalDate? = LocalDate.now(),
    @Column
    var endDate: LocalDate
) {
    override fun toString(): String {
        return "Project: $name, start date: $startDate, end date $endDate"
    }
}