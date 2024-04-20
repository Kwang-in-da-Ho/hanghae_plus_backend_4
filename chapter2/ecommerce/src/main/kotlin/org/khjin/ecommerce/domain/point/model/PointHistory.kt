package org.khjin.ecommerce.domain.point.model

class PointHistory(
    val pointHistoryId: Long,
    val customerId: Long,
    val amount: Long,
    val pointStatus: PointStatus,
){

}

enum class PointStatus{
    CHARGE,
    USE
}