package org.khjin.ecommerce.infrastructure.productinventory.repository

import org.khjin.ecommerce.domain.productinventory.model.ProductInventoryEntity
import org.springframework.stereotype.Repository

@Repository
class ProductInventoryCoreRepository: ProductInventoryRepository {

    override fun save(productInventoryEntity: ProductInventoryEntity): ProductInventoryEntity {
        TODO("Not yet implemented")
    }

    override fun findByProductId(productId: Long): ProductInventoryEntity {
        TODO("Not yet implemented")
    }
}