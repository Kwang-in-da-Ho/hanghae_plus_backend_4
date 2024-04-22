package org.khjin.ecommerce.infrastructure.point.repository

import org.khjin.ecommerce.domain.point.model.PointHistory

interface PointHistoryRepository {
    fun save(pointHistory: PointHistory)
}