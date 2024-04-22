package org.khjin.ecommerce.infrastructure.product.repository

import org.khjin.ecommerce.domain.product.model.Product

interface ProductRepository {

    fun save(product: Product): Product
}