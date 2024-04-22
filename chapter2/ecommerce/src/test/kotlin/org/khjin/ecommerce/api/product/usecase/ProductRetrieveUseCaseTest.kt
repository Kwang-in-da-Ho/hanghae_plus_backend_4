package org.khjin.ecommerce.api.product.usecase

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.khjin.ecommerce.domain.product.component.ProductComponent
import org.khjin.ecommerce.domain.product.exception.InvalidProductException
import org.khjin.ecommerce.domain.product.model.Product
import org.khjin.ecommerce.domain.productinventory.component.ProductInventoryComponent
import org.khjin.ecommerce.domain.productinventory.model.ProductInventoryEntity
import org.khjin.ecommerce.infrastructure.product.repository.ProductEntity
import org.khjin.ecommerce.infrastructure.product.repository.ProductRepository
import org.khjin.ecommerce.infrastructure.productinventory.repository.ProductInventoryRepository

class ProductRetrieveUseCaseTest{

    companion object{
        private val productStubRepository = ProductStubRepository()
        private val productInventoryStubRepository = ProductInventoryStubRepository()
        private val productComponent = ProductComponent(productStubRepository, productInventoryStubRepository)
        private val productInventoryComponent = ProductInventoryComponent()
        private val sut = ProductRetrieveUseCase(productComponent, productInventoryComponent)

        @JvmStatic
        @BeforeAll
        fun initRepo() {

        }
    }

    @Test
    fun `when product does not exist, throw InvalidProductException`() {

        assertThrows<InvalidProductException> {
            sut
        }
    }

    // stub repository
    private class ProductStubRepository: ProductRepository {
        private val db = mutableMapOf<Long, Product>()

        override fun save(productEntity: ProductEntity): ProductEntity {
            TODO("Not yet implemented")
        }

        override fun findById(productId: Long): ProductEntity? {
            TODO("Not yet implemented")
        }
    }

    private class ProductInventoryStubRepository: ProductInventoryRepository {
        override fun save(productInventoryEntity: ProductInventoryEntity): ProductInventoryEntity {
            TODO("Not yet implemented")
        }

        override fun findByProductId(productId: Long): ProductInventoryEntity {
            TODO("Not yet implemented")
        }
    }
}


