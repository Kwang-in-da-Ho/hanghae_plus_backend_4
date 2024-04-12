package org.khjin.ecommerce.api.product.usecase

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.khjin.ecommerce.domain.inventory.component.InventoryComponent
import org.khjin.ecommerce.domain.product.component.ProductComponent
import org.khjin.ecommerce.domain.product.repository.ProductRepository

class ProductRetrieveUseCaseTest{

    companion object{
        private val productRepository = ProductTestRepository()
        private val productComponent = ProductComponent(productRepository)
        private val inventoryComponent = InventoryComponent()
        private val sut = ProductRetrieveUseCase(productComponent, inventoryComponent)

        @JvmStatic
        @BeforeAll
        fun initRepo() {

        }
    }

    @Test
    fun `when product does not exist, throw InvalidProductException`() {
        sut.
    }

    // stub repository
    private class ProductTestRepository: ProductRepository{

    }
}