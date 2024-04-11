package org.khjin.ecommerce.api.order.controller

import org.khjin.ecommerce.api.order.dto.OrderRequest
import org.khjin.ecommerce.api.order.dto.OrderResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class OrderController {

    @PostMapping
    fun order(@RequestBody orderRequest: OrderRequest): OrderResponse {
        return OrderResponse(1234000L)
    }
}