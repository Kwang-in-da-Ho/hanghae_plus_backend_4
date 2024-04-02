package org.khjin.ecommerce.api.point.usecase

import org.khjin.ecommerce.api.point.dto.PointChargeRequest
import org.khjin.ecommerce.common.exception.InvalidPointException
import org.khjin.ecommerce.domain.point.component.PointComponent
import org.springframework.stereotype.Service

@Service
class PointChargeUsecase(
    private val pointComponent: PointComponent
) {
    fun chargePoint(request: PointChargeRequest) {
        if( request.point <= 0 ) throw InvalidPointException()
    }
}