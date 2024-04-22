package org.khjin.ecommerce.domain.point.model

import org.khjin.ecommerce.infrastructure.point.repository.PointStatus

data class PointHistory(
    val id: Long,
    val customerId: Long,
    val amount: Long,
    val pointStatus: PointStatus,
){
}
