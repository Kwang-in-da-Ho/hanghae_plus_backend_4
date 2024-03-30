package org.khjin.cleanarchitecture.user.repository

import org.khjin.cleanarchitecture.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository {

    fun findById(userId: Long): User?
    fun save(user: User): User
    fun deleteById(userId: Long)

}

interface UserJpaRepository: JpaRepository<org.apache.catalina.User, Long>, UserRepository {
}

class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
): UserRepository {
    override fun findById(userId: Long): User? {
        TODO("Not yet implemented")
    }

    override fun save(user: User): User {
        TODO("Not yet implemented")
    }

    override fun deleteById(userId: Long) {
        TODO("Not yet implemented")
    }
}