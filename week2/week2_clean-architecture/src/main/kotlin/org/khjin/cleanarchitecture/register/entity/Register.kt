package org.khjin.cleanarchitecture.register.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.khjin.cleanarchitecture.lecture.entity.Lecture
import org.khjin.cleanarchitecture.user.entity.User
import java.time.LocalDateTime

//Entity??

@Entity(name = "USER_LECTURE_REGISTER")
class Register(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val registerId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    val lecture: Lecture,

    val registerDateTime: LocalDateTime
) {

}