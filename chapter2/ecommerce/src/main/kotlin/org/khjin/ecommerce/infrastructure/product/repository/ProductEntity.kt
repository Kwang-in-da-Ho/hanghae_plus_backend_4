package org.khjin.ecommerce.infrastructure.product.repository

import jakarta.persistence.*

@Entity
@Table(name = "product")
class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val name: String,
    val category: ProductCategory,
    val price: Long
) {
}

enum class ProductCategory {
    SHIRTS,
    JEANS,
    BAGS,
    SWEATERS,
}
