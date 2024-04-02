package org.khjin.ecommerce.api.point.usecase

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.khjin.ecommerce.api.point.dto.PointChargeRequest
import org.khjin.ecommerce.common.exception.InvalidPointException
import org.khjin.ecommerce.domain.point.component.PointComponent
import org.khjin.ecommerce.domain.point.model.Point
import org.khjin.ecommerce.domain.point.repository.PointRepository

class PointUsecaseTest {

    companion object{
        private val pointStubRepository = PointStubRepository()
        private val pointComponent = PointComponent(pointStubRepository)
        private val sut = PointChargeUsecase(pointComponent)
    }

    @Test
    fun `when points given to charge is not a positive number, throw InvalidPointException`() {
        val negativeNumberRequest = PointChargeRequest(1L, -100)
        assertThrows<InvalidPointException>{
            sut.chargePoint(negativeNumberRequest)
        }

        val zeroPointRequest = PointChargeRequest(1L, 0)
        assertThrows<InvalidPointException>{
            sut.chargePoint(zeroPointRequest)
        }
    }

    // stub repo
    private class PointStubRepository: PointRepository{
        private val db = mutableMapOf<Long, Point>()
        override fun findByUserId(userId: Long) {
            TODO("Not yet implemented")
        }
    }
}