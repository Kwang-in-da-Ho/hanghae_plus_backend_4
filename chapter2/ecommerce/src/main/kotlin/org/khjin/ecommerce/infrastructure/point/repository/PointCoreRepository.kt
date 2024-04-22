package org.khjin.ecommerce.infrastructure.point.repository

import org.khjin.ecommerce.domain.point.model.Point
import org.springframework.stereotype.Repository

@Repository
class PointCoreRepository: PointRepository {
    override fun findByUserId(customerId: Long): Point? {
        TODO("Not yet implemented")
    }

    override fun save(point: Point): Point {
        TODO("Not yet implemented")
    }
}