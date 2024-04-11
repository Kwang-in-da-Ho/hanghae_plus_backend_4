package org.khjin.ecommerce.api.point.dto

data class ProductInfoResponse(
    val productId: Long,
    val productName: String,
    val category: String,
    val price: Long,
    val inventory: Long
) {

}
