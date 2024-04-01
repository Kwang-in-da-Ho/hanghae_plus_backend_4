package org.khjin.cleanarchitecture.lecture.repository

import org.khjin.cleanarchitecture.lecture.entity.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LectureRepository {

    fun findById(lectureId: Long): Lecture?
    fun save(lecture: Lecture): Lecture
    fun deleteById(lectureId: Long)
}

interface LectureJpaRepository: JpaRepository<Lecture, Long>, LectureRepository {

}

class LectureRepositoryImpl(
    private val lectureJpaRepository: LectureJpaRepository
): LectureRepository {
    override fun findById(lectureId: Long): Lecture? {
        TODO("Not yet implemented")
    }

    override fun save(lecture: Lecture): Lecture {
        TODO("Not yet implemented")
    }

    override fun deleteById(lectureId: Long) {
        TODO("Not yet implemented")
    }
}