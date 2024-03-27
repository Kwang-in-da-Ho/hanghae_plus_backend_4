package org.khjin.cleanarchitecture.register

import org.khjin.cleanarchitecture.register.dto.CheckRegistrationRequest
import org.khjin.cleanarchitecture.register.dto.CheckRegistrationResponse
import org.khjin.cleanarchitecture.register.dto.RegisterRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/register")
class RegisterController(
    private val registerService: RegisterService
) {

    @PostMapping
    fun register(@RequestBody registerRequest: RegisterRequest){
        registerService.register(registerRequest)
    }

    @GetMapping("/status/{userId}/{lectureId}")
    fun checkRegistrationStatus(@PathVariable userId:Long, @PathVariable lectureId: Long): CheckRegistrationResponse {
        return registerService.checkRegistration(CheckRegistrationRequest(userId, lectureId))
    }
}