package org.khjin.cleanarchitecture.register

import org.khjin.cleanarchitecture.lecture.LectureEntity
import org.khjin.cleanarchitecture.user.UserEntity
import java.time.LocalDateTime

//Entity??

class RegisterEntity(
    val userEntity: UserEntity,
    val lectureEntity: LectureEntity,
    val registerDateTime: LocalDateTime
) {
}