package org.khjin.ecommerce.domain.product.model

import org.khjin.ecommerce.infrastructure.product.repository.ProductCategory

data class Product(
    val id: Long,
    val name: String,
    val category: ProductCategory,
    val price: Long,
    val inventory: Long,
) {
}