package org.khjin.cleanarchitecture.lecture

import java.time.LocalDateTime

class LectureEntity(
    val lectureId: Long,
    val lectureName: String,
    val registerOpenDatetime: LocalDateTime,
    val studentCapacity: Int
) {
}