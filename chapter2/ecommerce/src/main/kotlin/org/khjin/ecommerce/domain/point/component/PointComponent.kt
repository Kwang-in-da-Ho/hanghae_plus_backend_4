package org.khjin.ecommerce.domain.point.component

import org.khjin.ecommerce.api.point.dto.PointChargeRequest
import org.khjin.ecommerce.domain.point.model.Point
import org.khjin.ecommerce.domain.point.repository.PointRepository
import org.springframework.stereotype.Component

@Component
class PointComponent(
    private val pointRepository: PointRepository
) {
    fun charge(point: Point): Point {
        return pointRepository.save(Point(point.userId, point.point))
    }
}