package org.khjin.cleanarchitecture.register.repository

import org.khjin.cleanarchitecture.lecture.entity.Lecture
import org.khjin.cleanarchitecture.register.entity.Register
import org.khjin.cleanarchitecture.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegisterRepository {
    fun save(user: User, lecture: Lecture): Register
    fun findById(user: User, lecture: Lecture): Register?
    fun deleteById(user: User, lecture: Lecture)
    fun findByLecture(lecture: Lecture): List<Register>

}


interface RegisterJpaRepository: JpaRepository<Register, Long>, RegisterRepository {

}

class RegisterRepositoryImpl(
    private val registerJpaRepository: RegisterJpaRepository
): RegisterRepository {
    override fun save(user: User, lecture: Lecture): Register {
        TODO("Not yet implemented")
    }

    override fun findById(user: User, lecture: Lecture): Register? {
        TODO("Not yet implemented")
    }

    override fun deleteById(user: User, lecture: Lecture) {
        TODO("Not yet implemented")
    }

    override fun findByLecture(lecture: Lecture): List<Register> {
        TODO("Not yet implemented")
    }
}