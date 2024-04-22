package org.khjin.ecommerce.domain.product.component

import org.khjin.ecommerce.domain.product.model.Product
import org.khjin.ecommerce.infrastructure.product.repository.ProductRepository
import org.khjin.ecommerce.infrastructure.productinventory.repository.ProductInventoryRepository
import org.springframework.stereotype.Component

@Component
class ProductComponent(
    private val productRepository: ProductRepository,
    private val productInventoryRepository: ProductInventoryRepository
) {
    fun retrieveProductInfoById(productId: Long): Product? {
        val productInfo = productRepository.findById(productId) ?: return null
        val inventory = productInventoryRepository.findByProductId(productId)

        return Product(productInfo.id!!, productInfo.name, productInfo.category, productInfo.price, inventory.quantity)
    }
}