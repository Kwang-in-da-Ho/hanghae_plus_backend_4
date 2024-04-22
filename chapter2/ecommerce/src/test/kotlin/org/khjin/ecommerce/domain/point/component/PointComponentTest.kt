
package org.khjin.ecommerce.domain.point.component

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.khjin.ecommerce.domain.point.model.Point
import org.khjin.ecommerce.infrastructure.point.repository.PointEntity
import org.khjin.ecommerce.infrastructure.point.repository.PointHistoryEntity
import org.khjin.ecommerce.infrastructure.point.repository.PointHistoryRepository
import org.khjin.ecommerce.infrastructure.point.repository.PointRepository

class PointComponentTest {

    companion object{
        private val pointStubRepository = PointStubRepository()
        private val pointHistoryStubRepository = PointHistoryStubRepository()
        private val sut = PointComponent(pointStubRepository, pointHistoryStubRepository)
    }

    @Test
    fun `user's point balance increases by the exact amount charged`() {
        val testModel = Point(0L, 5000L)
        val existing = pointStubRepository.findByCustomerId(testModel.customerId)
        val beforePoint = existing ?.amount ?: 0L
        val afterPoint = sut.charge(testModel).point

        assertEquals(beforePoint + testModel.point, afterPoint)

    }

    // stub repo
    private class PointStubRepository: PointRepository {
        private val db = mutableMapOf<Long, PointEntity>()
        override fun findByCustomerId(customerId: Long): PointEntity? {
            return db[customerId]
        }

        override fun save(pointEntity: PointEntity): PointEntity {
            if(db[pointEntity.customerId] == null){
                db[pointEntity.customerId!!] = pointEntity
            }else {
                val old = db[pointEntity.customerId]!!
                val new = PointEntity(pointEntity.customerId!!, pointEntity.amount + old.amount)
                db[pointEntity.customerId!!] = new
            }

            return db[pointEntity.customerId]!!
        }
    }

    private class PointHistoryStubRepository: PointHistoryRepository {
        private val db = mutableMapOf<Long, PointHistoryEntity>()
        private var sequence = 0L

        override fun save(pointHistoryEntity: PointHistoryEntity): PointHistoryEntity {
            db[++sequence] = pointHistoryEntity
            return pointHistoryEntity
        }
    }
}