package org.khjin.ecommerce.api.point.usecase

import org.junit.jupiter.api.Test
import org.khjin.ecommerce.api.point.dto.PointChargeInputDto
import org.khjin.ecommerce.common.exception.InvalidPointException
import org.khjin.ecommerce.domain.point.component.PointComponent
import org.khjin.ecommerce.domain.point.model.Point
import org.khjin.ecommerce.domain.point.model.PointHistory
import org.khjin.ecommerce.domain.point.repository.PointHistoryRepository
import org.khjin.ecommerce.domain.point.repository.PointRepository

class PointChargeUseCaseTest{

    companion object{
        private val pointStubRepository = PointStubRepository()
        private val pointHistoryStubRepository = PointHistoryStubRepository()
        private val pointComponent = PointComponent(pointStubRepository, pointHistoryStubRepository)
        private val sut = PointChargeUseCase(pointComponent)
    }

    @Test
    fun `when points given to charge is not a positive number, throw InvalidPointException`() {
        val negativeNumberRequest = PointChargeInputDto(1L, -100)
        org.junit.jupiter.api.assertThrows<InvalidPointException> {
            sut.run(negativeNumberRequest)
        }

        val zeroPointRequest = PointChargeInputDto(1L, 0)
        org.junit.jupiter.api.assertThrows<InvalidPointException> {
            sut.run(zeroPointRequest)
        }
    }

    // stub repo
    private class PointStubRepository: PointRepository {
        private val db = mutableMapOf<Long, Point>()
        override fun findByUserId(userId: Long): Point? {
            return db[userId]
        }

        override fun save(point: Point): Point {
            if(db[point.customerId] == null){
                db[point.customerId] = point
            }else {
                val old = db[point.customerId]!!
                val new = Point(point.customerId, point.point + old.point)
                db[point.customerId] = new
            }

            return db[point.customerId]!!
        }
    }

    private class PointHistoryStubRepository: PointHistoryRepository {
        private val db = mutableMapOf<Long, PointHistory>()
        private var sequence = 0L
        override fun save(pointHistory: PointHistory) {
            db[++sequence] = pointHistory
        }
    }

}