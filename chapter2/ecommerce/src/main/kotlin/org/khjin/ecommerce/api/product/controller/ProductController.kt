package org.khjin.ecommerce.api.product.controller

import org.khjin.ecommerce.api.point.dto.ProductInfoResponse
import org.khjin.ecommerce.api.product.dto.TopSellingProductResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/product")
class ProductController {

    @GetMapping("/{productId}")
    fun retrieveProductInfo(@PathVariable productId: Int): ProductInfoResponse {
        return ProductInfoResponse(0L, "test", "test category", 1000L, 1234L)
    }

    @GetMapping("/top-selling")
    fun retrieveTopSellingProducts(): List<TopSellingProductResponse>{
        return listOf(
            TopSellingProductResponse(0L, "test", Date(), Date(), 1234L)
        )
    }
}