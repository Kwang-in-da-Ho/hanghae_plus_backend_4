package org.khjin.ecommerce.infrastructure.productinventory.repository

import org.khjin.ecommerce.domain.productinventory.model.ProductInventoryEntity

interface ProductInventoryRepository {

    fun save(productInventoryEntity: ProductInventoryEntity): ProductInventoryEntity

    fun findByProductId(productId: Long): ProductInventoryEntity

}