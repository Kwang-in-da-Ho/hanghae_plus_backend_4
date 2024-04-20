package org.khjin.ecommerce.api.point.dto


data class PointChargeRequest(
    val customerId: Long,
    val point: Long
) {
    fun toInputDto(): PointChargeInputDto {
        return PointChargeInputDto(this.customerId, this.point)
    }
}