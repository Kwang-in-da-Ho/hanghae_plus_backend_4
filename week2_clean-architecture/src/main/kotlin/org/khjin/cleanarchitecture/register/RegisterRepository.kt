package org.khjin.cleanarchitecture.register

import org.khjin.cleanarchitecture.lecture.LectureEntity
import org.khjin.cleanarchitecture.user.UserEntity
import org.springframework.stereotype.Repository

@Repository
interface RegisterRepository {
    fun save(user: UserEntity, lecture: LectureEntity): RegisterEntity
    fun findById(user: UserEntity, lecture: LectureEntity): RegisterEntity?
    fun deleteById(user: UserEntity, lecture: LectureEntity)
    fun findByLecture(lecture: LectureEntity): List<RegisterEntity>

}