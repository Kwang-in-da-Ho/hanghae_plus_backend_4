package org.khjin.ecommerce.api.point.usecase

import org.khjin.ecommerce.api.point.dto.PointChargeInputDto
import org.khjin.ecommerce.api.point.dto.PointChargeOutputDto
import org.khjin.ecommerce.domain.point.exception.InvalidPointException
import org.khjin.ecommerce.domain.point.component.PointComponent
import org.springframework.stereotype.Service

@Service
class PointChargeUseCase(
    private val pointComponent: PointComponent
) {
    fun run(input: PointChargeInputDto): PointChargeOutputDto {
        if( input.point <= 0 ) throw InvalidPointException()

        val charged = pointComponent.charge(input.toEntity()).point
        return PointChargeOutputDto(charged)
    }
}
