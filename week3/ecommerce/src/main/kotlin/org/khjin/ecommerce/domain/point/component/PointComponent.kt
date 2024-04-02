package org.khjin.ecommerce.domain.point.component

import org.khjin.ecommerce.domain.point.repository.PointRepository
import org.springframework.stereotype.Component

@Component
class PointComponent(
    private val pointRepository: PointRepository
) {
}