package org.khjin.ecommerce.api.point.usecase

import org.khjin.ecommerce.api.point.dto.PointChargeRequest
import org.khjin.ecommerce.api.point.dto.PointChargeResponse
import org.khjin.ecommerce.common.exception.InvalidPointException
import org.khjin.ecommerce.domain.point.component.PointComponent
import org.springframework.stereotype.Service

@Service
class PointChargeUseCase(
    private val pointComponent: PointComponent
) {
    fun chargePoint(request: PointChargeRequest): PointChargeResponse {
        if( request.point <= 0 ) throw InvalidPointException()

        val charged = pointComponent.charge(request.toEntity())
        return PointChargeResponse(charged.userId, charged.point)
    }
}