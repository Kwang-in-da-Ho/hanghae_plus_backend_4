package org.khjin.ecommerce.infrastructure.point.repository

import org.khjin.ecommerce.domain.point.model.Point

interface PointRepository {
    fun findByUserId(customerId: Long): Point?

    fun save(point: Point): Point
}