
package org.khjin.ecommerce.domain.point.component

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.khjin.ecommerce.domain.point.model.Point
import org.khjin.ecommerce.domain.point.repository.PointRepository

class PointComponentTest {

    companion object{
        private val pointStubRepository = PointStubRepository()
        private val sut = PointComponent(pointStubRepository)
    }

    @Test
    fun `user's point balance increases by the exact amount charged`() {
        val testModel = Point(0L, 5000L)
        val existing = pointStubRepository.findByUserId(testModel.userId)
        val beforePoint = existing ?.point ?: 0L
        val afterPoint = sut.charge(testModel).point

        assertEquals(beforePoint + testModel.point, afterPoint)

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