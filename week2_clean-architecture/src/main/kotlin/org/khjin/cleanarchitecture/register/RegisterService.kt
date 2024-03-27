package org.khjin.cleanarchitecture.register

import org.khjin.cleanarchitecture.exception.*
import org.khjin.cleanarchitecture.lecture.LectureRepository
import org.khjin.cleanarchitecture.user.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RegisterService(
    private val registerRepository: RegisterRepository,
    private val userRepository: UserRepository,
    private val lectureRepository: LectureRepository,
) {
    fun register(userId: Long, lectureId: Long) {

        val user = userRepository.findById(userId) ?: throw InvalidUserException()
        val lecture = lectureRepository.findById(lectureId) ?: throw InvalidLectureException()

        if( lecture.registerOpenDatetime.isAfter(LocalDateTime.now()) ){
            throw LectureRegistrationNotOpenException()
        }

        val registerList = registerRepository.findByLectureId(lectureId)
        if( lecture.studentCapacity <=  registerList.size){
            throw StudentsFullException()
        }

        val prevRegister = registerRepository.findById(user, lecture)
        if(prevRegister != null) {
            throw DuplicateRegistrationException()
        }

        registerRepository.save(user, lecture)
    }

}