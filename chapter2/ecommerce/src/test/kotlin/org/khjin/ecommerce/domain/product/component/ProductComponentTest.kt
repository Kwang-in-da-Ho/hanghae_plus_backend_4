package org.khjin.ecommerce.domain.product.component

import org.junit.jupiter.api.Test
import org.khjin.ecommerce.domain.product.model.Product
import org.khjin.ecommerce.domain.productinventory.model.ProductInventory
import org.khjin.ecommerce.infrastructure.product.repository.ProductRepository
import org.khjin.ecommerce.infrastructure.productinventory.repository.ProductInventoryRepository

class ProductComponentTest{

    private val productStubRepository = ProductStubRepository()
    private val sut = ProductComponent(productStubRepository)

    @Test
    fun `should return all product info and inventory`() {

    }

    //stub repos
    private class ProductStubRepository: ProductRepository {
        private val db = mutableMapOf<Long, Product>()

        override fun save(product: Product): Product {
            TODO("Not yet implemented")
        }
    }

    private class ProductInventoryStubRepository: ProductInventoryRepository{
        private val db = mutableMapOf<Long, ProductInventory>()
    }

}