
package org.khjin.ecommerce.domain.point.component

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.khjin.ecommerce.domain.point.model.Point
import org.khjin.ecommerce.domain.point.model.PointHistory
import org.khjin.ecommerce.domain.point.repository.PointHistoryRepository
import org.khjin.ecommerce.domain.point.repository.PointRepository

class PointComponentTest {

    companion object{
        private val pointStubRepository = PointStubRepository()
        private val pointHistoryStubRepository = PointHistoryStubRepository()
        private val sut = PointComponent(pointStubRepository, pointHistoryStubRepository)
    }

    @Test
    fun `user's point balance increases by the exact amount charged`() {
        val testModel = Point(0L, 5000L)
        val existing = pointStubRepository.findByUserId(testModel.customerId)
        val beforePoint = existing ?.point ?: 0L
        val afterPoint = sut.charge(testModel).point

        assertEquals(beforePoint + testModel.point, afterPoint)

    }

    // stub repo
    private class PointStubRepository: PointRepository {
        private val db = mutableMapOf<Long, Point>()
        override fun findByUserId(customerId: Long): Point? {
            return db[customerId]
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
            sequence++
            db[sequence] = pointHistory
        }
    }
}