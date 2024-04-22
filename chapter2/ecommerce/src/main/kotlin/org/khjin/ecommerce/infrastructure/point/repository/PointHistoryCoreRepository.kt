package org.khjin.ecommerce.infrastructure.point.repository

import org.khjin.ecommerce.domain.point.model.PointHistory
import org.springframework.stereotype.Repository

@Repository
class PointHistoryCoreRepository: PointHistoryRepository {
    override fun save(pointHistory: PointHistory) {
        TODO("Not yet implemented")
    }
}