package org.khjin.ecommerce.domain.point.repository

interface PointRepository {
    fun findByUserId(userId: Long)
}