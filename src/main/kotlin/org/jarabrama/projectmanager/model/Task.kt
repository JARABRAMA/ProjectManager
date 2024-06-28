package org.jarabrama.projectmanager.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
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
    var status: String,
    @Column
    val date: Date,
)