package org.khjin.ecommerce.api.point.dto

import org.khjin.ecommerce.domain.point.model.Point

data class PointChargeInputDto(
    val customerId: Long,
    val point: Long,
){
    fun toEntity(): Point{
        return Point(this.customerId, this.point)
    }
}

data class PointChargeOutputDto(
    val point:Long
){
    fun toResponse(): PointChargeResponse {
        return PointChargeResponse(this.point)
    }
}