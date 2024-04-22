package org.khjin.ecommerce.infrastructure.point.repository

interface PointHistoryRepository {
    fun save(pointHistoryEntity: PointHistoryEntity): PointHistoryEntity
}
