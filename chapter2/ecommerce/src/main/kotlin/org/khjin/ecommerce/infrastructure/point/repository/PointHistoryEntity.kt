package org.khjin.ecommerce.infrastructure.point.repository

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "point_history")
class PointHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val customerId: Long,
    val amount: Long,
    val pointStatus: PointStatus,
    val lastUpdated: LocalDateTime,
) {
}

enum class PointStatus {
    CHARGE,
    USE
}
