package org.khjin.ecommerce.infrastructure.point.repository

import org.khjin.ecommerce.domain.point.model.Point
import org.springframework.stereotype.Repository

@Repository
class PointCoreRepository: PointRepository {
    override fun findByCustomerId(customerId: Long): PointEntity? {
        TODO("Not yet implemented")
    }

    override fun save(pointEntity: PointEntity): PointEntity {
        TODO("Not yet implemented")
    }
}