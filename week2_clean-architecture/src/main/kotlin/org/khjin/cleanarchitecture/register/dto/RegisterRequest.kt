package org.khjin.cleanarchitecture.register.dto

data class RegisterRequest(val userId: Long, val lectureId: Long) {
}

data class CheckRegistrationRequest(val userId: Long, val lectureId: Long) {

}
data class CheckRegistrationResponse(val registrationStatus: RegistrationStatus) {}

enum class RegistrationStatus(){
    SUCCESS,
    FAIL
}