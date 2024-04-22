package org.khjin.ecommerce.domain.productinventory.model

import java.time.LocalDateTime

data class ProductInventoryEntity(
    val productId: Long,
    val quantity: Long,
    val lastUpdated: LocalDateTime
) {
}