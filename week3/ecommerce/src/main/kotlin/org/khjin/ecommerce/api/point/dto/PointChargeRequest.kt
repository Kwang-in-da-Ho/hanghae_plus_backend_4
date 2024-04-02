package org.khjin.ecommerce.api.point.dto

data class PointChargeRequest(
    val userId: Long,
    val point: Long
) {
}