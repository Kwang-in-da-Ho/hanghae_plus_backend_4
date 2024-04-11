package org.khjin.ecommerce.api.product.dto

import java.util.Date

data class TopSellingProductResponse(
    val productId: Long,
    val productName: String,
    val startDate: Date,
    val endDate: Date,
    val soldQuantity: Long,
) {

}
