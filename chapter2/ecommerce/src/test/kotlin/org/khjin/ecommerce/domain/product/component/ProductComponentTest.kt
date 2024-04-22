package org.khjin.ecommerce.domain.product.component

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.khjin.ecommerce.domain.product.model.Product
import org.khjin.ecommerce.domain.productinventory.model.ProductInventoryEntity
import org.khjin.ecommerce.infrastructure.product.repository.ProductCategory
import org.khjin.ecommerce.infrastructure.product.repository.ProductEntity
import org.khjin.ecommerce.infrastructure.product.repository.ProductRepository
import org.khjin.ecommerce.infrastructure.productinventory.repository.ProductInventoryRepository
import java.time.LocalDateTime

class ProductComponentTest{

    companion object {
        private val productStubRepository = ProductStubRepository()
        private val productInventoryStubRepository = ProductInventoryStubRepository()
        private val sut = ProductComponent(productStubRepository, productInventoryStubRepository)

        private val product1 = ProductEntity(0L, "빈폴 티셔츠", ProductCategory.SHIRTS, 120000)
        private val product2 = ProductEntity(1L, "빈폴 청바지", ProductCategory.JEANS, 230000)
        private val product1Inventory = ProductInventoryEntity(product1.id!!, 123, LocalDateTime.now())
        private val product2Inventory = ProductInventoryEntity(product2.id!!, 23, LocalDateTime.now())

        @JvmStatic
        @BeforeAll
        fun initRepo() {
            productStubRepository.save(product1)
            productStubRepository.save(product2)
            productInventoryStubRepository.save(product1Inventory)
            productInventoryStubRepository.save(product2Inventory)
        }
    }

    @Test
    fun `should return all product info and inventory`() {
        val result = sut.retrieveProductInfoById(product1.id!!)
        assertNotNull(result)
        assertEquals(product1.id, result!!.id)
        assertEquals(product1.name, result.name)
        assertEquals(product1.category, result.category)
        assertEquals(product1.price, result.price)
        assertEquals(product1Inventory.quantity, result.inventory)

    }

    //stub repos
    private class ProductStubRepository: ProductRepository {
        private val db = mutableMapOf<Long, ProductEntity>()

        override fun save(productEntity: ProductEntity): ProductEntity {
            db[productEntity.id!!] = productEntity
            return productEntity
        }

        override fun findById(productId: Long): ProductEntity? {
            return db[productId]
        }
    }

    private class ProductInventoryStubRepository: ProductInventoryRepository{
        private val db = mutableMapOf<Long, ProductInventoryEntity>()
        override fun save(productInventoryEntity: ProductInventoryEntity): ProductInventoryEntity {
            db[productInventoryEntity.productId] = productInventoryEntity
            return productInventoryEntity
        }

        override fun findByProductId(productId: Long): ProductInventoryEntity {
            return db[productId]!!
        }


    }

}