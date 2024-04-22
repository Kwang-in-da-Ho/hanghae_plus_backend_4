package org.khjin.ecommerce.infrastructure.product.repository

interface ProductRepository {

    fun save(productEntity: ProductEntity): ProductEntity
    fun findById(productId: Long): ProductEntity?
}