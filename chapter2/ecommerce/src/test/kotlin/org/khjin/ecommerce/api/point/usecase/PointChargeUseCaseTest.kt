package org.khjin.ecommerce.api.point.usecase

import org.junit.jupiter.api.Test
import org.khjin.ecommerce.api.point.dto.PointChargeRequest
import org.khjin.ecommerce.common.exception.InvalidPointException
import org.khjin.ecommerce.domain.point.component.PointComponent
import org.khjin.ecommerce.domain.point.model.Point
import org.khjin.ecommerce.domain.point.repository.PointRepository

class PointChargeUseCaseTest{

    companion object{
        private val pointStubRepository = PointStubRepository()
        private val pointComponent = PointComponent(pointStubRepository)
        private val sut = PointChargeUseCase(pointComponent)
    }

    @Test
    fun `when points given to charge is not a positive number, throw InvalidPointException`() {
        val negativeNumberRequest = PointChargeRequest(1L, -100)
        org.junit.jupiter.api.assertThrows<InvalidPointException> {
            sut.chargePoint(negativeNumberRequest)
        }

        val zeroPointRequest = PointChargeRequest(1L, 0)
        org.junit.jupiter.api.assertThrows<InvalidPointException> {
            sut.chargePoint(zeroPointRequest)
        }
    }

    // stub repo
    private class PointStubRepository: PointRepository {
        private val db = mutableMapOf<Long, Point>()
        override fun findByUserId(userId: Long): Point? {
            return db[userId]
        }

        override fun save(point: Point): Point {
            if(db[point.userId] == null){
                db[point.userId] = point
            }else {
                val old = db[point.userId]!!
                val new = Point(point.userId, point.point + old.point)
                db[point.userId] = new
            }

            return db[point.userId]!!
        }
    }

}