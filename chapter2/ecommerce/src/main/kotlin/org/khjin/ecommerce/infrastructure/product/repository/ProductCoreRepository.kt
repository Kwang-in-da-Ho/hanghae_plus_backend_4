package org.khjin.ecommerce.infrastructure.product.repository

import org.khjin.ecommerce.domain.product.model.Product
import org.springframework.stereotype.Repository

@Repository
class ProductCoreRepository: ProductRepository {
    override fun save(product: Product): Product {
        TODO("Not yet implemented")
    }
}