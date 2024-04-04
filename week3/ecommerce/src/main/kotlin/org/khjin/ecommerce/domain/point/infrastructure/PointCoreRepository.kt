package org.khjin.ecommerce.domain.point.infrastructure

import org.khjin.ecommerce.domain.point.model.Point
import org.khjin.ecommerce.domain.point.repository.PointRepository
import org.springframework.stereotype.Repository

@Repository
class PointCoreRepository: PointRepository {
    override fun findByUserId(userId: Long): Point? {
        TODO("Not yet implemented")
    }

    override fun save(point: Point): Point {
        TODO("Not yet implemented")
    }
}