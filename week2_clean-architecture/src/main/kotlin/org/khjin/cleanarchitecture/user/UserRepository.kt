package org.khjin.cleanarchitecture.user

import org.springframework.stereotype.Repository

@Repository
interface UserRepository {

    fun findById(userId: Long): UserEntity?
    fun save(user: UserEntity): UserEntity
    fun deleteById(userId: Long)

}