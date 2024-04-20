package org.khjin.ecommerce.api.point.controller

import org.khjin.ecommerce.api.point.dto.PointChargeRequest
import org.khjin.ecommerce.api.point.dto.PointChargeResponse
import org.khjin.ecommerce.api.point.usecase.PointChargeUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/point")
class PointController(
    private val pointChargeUseCase: PointChargeUseCase
) {

    @PostMapping("/charge")
    fun charge(@RequestBody pointChargeRequest: PointChargeRequest): PointChargeResponse{
        return pointChargeUseCase.run(pointChargeRequest.toInputDto()).toResponse()
    }

}