package org.jarabrama.projectmanager.model.entity

import jakarta.persistence.*
import org.jarabrama.projectmanager.model.enums.Status
import java.time.LocalDate
import java.util.Date

@Entity
@Table
class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = 0,
    @Column
    var title: String,
    @Column
    var description: String,
    @Column
    var developerId: Long,
    @Column
    var projectId: Long,
    @Column
    @Enumerated(value = EnumType.STRING)
    var status: Status?  = Status.TODO,
    @Column
    val initDate: LocalDate? = LocalDate.now(),
    @Column
    val finishDate: LocalDate
)