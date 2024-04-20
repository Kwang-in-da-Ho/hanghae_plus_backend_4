package org.khjin.ecommerce.domain.point.repository

import org.khjin.ecommerce.domain.point.model.PointHistory

interface PointHistoryRepository {
    fun save(pointHistory: PointHistory)
}