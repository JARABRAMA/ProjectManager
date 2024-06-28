package org.jarabrama.projectmanager.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.jarabrama.projectmanager.model.enums.Role

@Entity
@Table
class Developer(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long = 0,
    @Column
    var fullName: String,
    @Column
    var email: String,
    @Column
    var password: String,
    @Column
    var role: Role
)