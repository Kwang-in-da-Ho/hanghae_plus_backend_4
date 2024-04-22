package org.khjin.ecommerce.api.product.usecase

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.khjin.ecommerce.domain.productinventory.component.ProductInventoryComponent
import org.khjin.ecommerce.domain.product.component.ProductComponent
import org.khjin.ecommerce.domain.product.model.Product
import org.khjin.ecommerce.infrastructure.product.repository.ProductRepository

class ProductRetrieveUseCaseTest{

    companion object{
        private val productRepository = ProductStubRepository()
        private val productComponent = ProductComponent(productRepository)
        private val inventoryComponent = ProductInventoryComponent()
        private val sut = ProductRetrieveUseCase(productComponent, inventoryComponent)

        @JvmStatic
        @BeforeAll
        fun initRepo() {

        }
    }

    @Test
    fun `when product does not exist, throw InvalidProductException`() {
    }

    // stub repository
    private class ProductStubRepository: ProductRepository {
        override fun save(product: Product): Product {
            TODO("Not yet implemented")
        }
    }
}