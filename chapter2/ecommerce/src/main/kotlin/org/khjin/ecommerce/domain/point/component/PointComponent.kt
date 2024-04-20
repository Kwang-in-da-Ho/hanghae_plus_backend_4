package org.khjin.ecommerce.domain.point.component

import org.khjin.ecommerce.domain.point.model.Point
import org.khjin.ecommerce.domain.point.model.PointHistory
import org.khjin.ecommerce.domain.point.model.PointStatus
import org.khjin.ecommerce.domain.point.repository.PointHistoryRepository
import org.khjin.ecommerce.domain.point.repository.PointRepository
import org.springframework.stereotype.Component

@Component
class PointComponent(
    private val pointRepository: PointRepository,
    private val pointHistoryRepository: PointHistoryRepository,
) {
    fun charge(point: Point): Point {

        val result = pointRepository.save(Point(point.customerId, point.point))
        pointHistoryRepository.save(PointHistory(
            0L, point.customerId, point.point, PointStatus.CHARGE
        ))

        return result
    }
}