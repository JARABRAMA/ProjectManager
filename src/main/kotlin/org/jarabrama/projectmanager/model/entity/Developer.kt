package org.jarabrama.projectmanager.model.entity

import jakarta.persistence.*
import org.jarabrama.projectmanager.model.enums.Role

@Entity
@Table
class Developer(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long? = 0,
    @Column
    var fullName: String,
    @Column(unique = true)
    var email: String,
    @Column
    var password: String,
    @Column
    @Enumerated(value = EnumType.STRING)
    var role: Role
) {
    override fun toString(): String {
        return "developer: $fullName, email: $email, password: $password, role: $role, id: $id"
    }
}