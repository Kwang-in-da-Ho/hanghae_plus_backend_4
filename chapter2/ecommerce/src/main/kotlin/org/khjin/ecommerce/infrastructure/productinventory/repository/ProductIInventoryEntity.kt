package org.khjin.ecommerce.infrastructure.productinventory.repository

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "product_inventory")
class ProductIInventoryEntity(
    @Id
    val productId: Long,
    val quantity: Long,
    val lastUpdated: LocalDateTime,
) {

}