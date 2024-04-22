package org.khjin.ecommerce.domain.product.component

import org.khjin.ecommerce.infrastructure.product.repository.ProductRepository
import org.springframework.stereotype.Component

@Component
class ProductComponent(
    private val productRepository: ProductRepository
) {
}