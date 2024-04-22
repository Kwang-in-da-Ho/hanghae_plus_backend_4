package org.khjin.ecommerce.infrastructure.point.repository

interface PointRepository {
    fun findByCustomerId(customerId: Long): PointEntity?
    fun save(pointEntity: PointEntity): PointEntity
}