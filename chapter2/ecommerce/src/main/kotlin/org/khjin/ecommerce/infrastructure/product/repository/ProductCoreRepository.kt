package org.khjin.ecommerce.infrastructure.product.repository

import org.khjin.ecommerce.domain.product.model.Product
import org.springframework.stereotype.Repository

@Repository
class ProductCoreRepository: ProductRepository {
    override fun save(productEntity: ProductEntity): ProductEntity {
        TODO("Not yet implemented")
    }

    override fun findById(productId: Long): ProductEntity? {
        TODO("Not yet implemented")
    }
}