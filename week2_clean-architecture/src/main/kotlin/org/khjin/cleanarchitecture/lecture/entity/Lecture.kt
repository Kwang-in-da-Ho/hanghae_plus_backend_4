package org.khjin.cleanarchitecture.lecture.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.LocalDateTime


@Entity
class Lecture(
    @Id
    @GeneratedValue
    val lectureId: Long,
    val lectureName: String,
    val registerOpenDatetime: LocalDateTime,
    val studentCapacity: Int
) {
}