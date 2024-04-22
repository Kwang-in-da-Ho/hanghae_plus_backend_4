package org.khjin.ecommerce.domain.point.component

import org.khjin.ecommerce.domain.point.model.Point
import org.khjin.ecommerce.infrastructure.point.repository.*
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class PointComponent(
    private val pointRepository: PointRepository,
    private val pointHistoryRepository: PointHistoryRepository,
) {
    fun charge(pointEntity: Point): Point {

        val result = pointRepository.save(PointEntity(pointEntity.customerId, pointEntity.point)).toModel()
        pointHistoryRepository.save(PointHistoryEntity(
            null, pointEntity.customerId, pointEntity.point, PointStatus.CHARGE, LocalDateTime.now()
        ))

        return result
    }
}