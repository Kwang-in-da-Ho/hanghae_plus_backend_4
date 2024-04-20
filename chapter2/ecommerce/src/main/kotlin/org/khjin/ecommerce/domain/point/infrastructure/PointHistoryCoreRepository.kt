package org.khjin.ecommerce.domain.point.infrastructure

import org.khjin.ecommerce.domain.point.model.PointHistory
import org.khjin.ecommerce.domain.point.repository.PointHistoryRepository
import org.springframework.stereotype.Repository

@Repository
class PointHistoryCoreRepository: PointHistoryRepository {
    override fun save(pointHistory: PointHistory) {
        TODO("Not yet implemented")
    }
}