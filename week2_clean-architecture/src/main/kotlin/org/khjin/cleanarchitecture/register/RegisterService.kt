package org.khjin.cleanarchitecture.register

import org.khjin.cleanarchitecture.exception.*
import org.khjin.cleanarchitecture.lecture.LectureRepository
import org.khjin.cleanarchitecture.register.dto.CheckRegistrationRequest
import org.khjin.cleanarchitecture.register.dto.CheckRegistrationResponse
import org.khjin.cleanarchitecture.register.dto.RegisterRequest
import org.khjin.cleanarchitecture.register.dto.RegistrationStatus
import org.khjin.cleanarchitecture.user.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RegisterService(
    private val registerRepository: RegisterRepository,
    private val userRepository: UserRepository,
    private val lectureRepository: LectureRepository,
) {
    fun register(registerRequest: RegisterRequest) {

        val user = userRepository.findById(registerRequest.userId) ?: throw InvalidUserException()
        val lecture = lectureRepository.findById(registerRequest.lectureId) ?: throw InvalidLectureException()

        if( lecture.registerOpenDatetime.isAfter(LocalDateTime.now()) ){
            throw LectureRegistrationNotOpenException()
        }

        val registerList = registerRepository.findByLecture(lecture)
        if( lecture.studentCapacity <=  registerList.size){
            throw StudentsFullException()
        }

        val prevRegister = registerRepository.findById(user, lecture)
        if(prevRegister != null) {
            throw DuplicateRegistrationException()
        }

        registerRepository.save(user, lecture)
    }

    fun checkRegistration(checkRegistrationRequest: CheckRegistrationRequest): CheckRegistrationResponse {

        val user = userRepository.findById(checkRegistrationRequest.userId) ?: throw InvalidUserException()
        val lecture = lectureRepository.findById(checkRegistrationRequest.lectureId) ?: throw InvalidLectureException()

        val result = CheckRegistrationResponse(
            if( registerRepository.findById(user, lecture) != null )
                RegistrationStatus.SUCCESS
            else
                RegistrationStatus.FAIL
        )

        return result
    }

}