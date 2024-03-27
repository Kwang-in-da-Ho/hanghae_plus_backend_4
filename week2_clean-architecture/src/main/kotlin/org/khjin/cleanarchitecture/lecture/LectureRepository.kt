package org.khjin.cleanarchitecture.lecture

import org.springframework.stereotype.Repository

@Repository
interface LectureRepository {

    fun findById(lectureId: Long): LectureEntity?
    fun save(lecture: LectureEntity): LectureEntity
    fun deleteById(lectureId: Long)
}