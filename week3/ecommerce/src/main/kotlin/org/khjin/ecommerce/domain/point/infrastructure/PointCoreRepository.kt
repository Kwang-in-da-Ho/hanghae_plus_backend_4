package org.khjin.ecommerce.domain.point.infrastructure

import org.khjin.ecommerce.domain.point.repository.PointRepository
import org.springframework.stereotype.Repository

@Repository
class PointCoreRepository: PointRepository {
    override fun findByUserId(userId: Long) {
        TODO("Not yet implemented")
    }
}