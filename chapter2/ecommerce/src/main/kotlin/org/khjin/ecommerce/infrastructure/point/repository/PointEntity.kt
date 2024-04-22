package org.khjin.ecommerce.infrastructure.point.repository

import jakarta.persistence.*
import org.khjin.ecommerce.domain.point.model.Point

@Entity
@Table(name = "point")
class PointEntity(
    @Id
    val customerId: Long,
    val amount: Long,
) {
    fun toModel(): Point{
        return Point(this.customerId, this.amount)
    }
}