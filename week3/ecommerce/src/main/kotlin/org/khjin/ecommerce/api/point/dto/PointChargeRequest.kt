package org.khjin.ecommerce.api.point.dto

import org.khjin.ecommerce.domain.point.model.Point

data class PointChargeRequest(
    val userId: Long,
    val point: Long
) {
    fun toEntity(): Point {
        return Point(this.userId, this.point)
    }
}