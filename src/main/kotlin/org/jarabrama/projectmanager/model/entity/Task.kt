package org.jarabrama.projectmanager.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.jarabrama.projectmanager.model.enums.Status
import java.util.Date

@Entity
class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long = 0,
    @Column
    var title: String,
    @Column
    var description: String,
    @Column
    var developerId: Long,
    @Column
    var projectId: Long,
    @Column
    var status: Status,
    @Column
    val date: Date,
)