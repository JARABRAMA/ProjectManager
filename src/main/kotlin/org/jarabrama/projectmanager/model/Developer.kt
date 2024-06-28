package org.jarabrama.projectmanager.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import javax.print.attribute.standard.RequestingUserName

@Entity
@Table
class Developer(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long = 0,
    @Column
    var username: String,
    @Column
    var email: String,
    @Column
    var password: String
)