package org.khjin.ecommerce.api.product.usecase

import org.khjin.ecommerce.domain.productinventory.component.ProductInventoryComponent
import org.khjin.ecommerce.domain.product.component.ProductComponent
import org.springframework.stereotype.Service

@Service
class ProductRetrieveUseCase(
    private val productComponent: ProductComponent,
    private val productInventoryComponent: ProductInventoryComponent
) {
}