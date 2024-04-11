package org.khjin.ecommerce.domain.point.repository

import org.khjin.ecommerce.domain.point.model.Point

interface PointRepository {
    fun findByUserId(userId: Long): Point?

    fun save(point: Point): Point
}