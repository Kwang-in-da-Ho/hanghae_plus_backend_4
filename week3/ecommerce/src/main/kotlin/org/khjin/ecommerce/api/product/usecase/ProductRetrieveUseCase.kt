package org.khjin.ecommerce.api.product.usecase

import org.khjin.ecommerce.domain.inventory.component.InventoryComponent
import org.khjin.ecommerce.domain.product.component.ProductComponent
import org.springframework.stereotype.Service

@Service
class ProductRetrieveUseCase(
    private val productComponent: ProductComponent,
    private val inventoryComponent: InventoryComponent
) {
}