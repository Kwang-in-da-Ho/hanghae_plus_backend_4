package org.khjin.ecommerce.api.point.usecase

import org.junit.jupiter.api.Test
import org.khjin.ecommerce.api.point.dto.PointChargeInputDto
import org.khjin.ecommerce.domain.point.exception.InvalidPointException
import org.khjin.ecommerce.domain.point.component.PointComponent
import org.khjin.ecommerce.domain.point.model.Point
import org.khjin.ecommerce.infrastructure.point.repository.PointEntity
import org.khjin.ecommerce.infrastructure.point.repository.PointHistoryEntity
import org.khjin.ecommerce.infrastructure.point.repository.PointHistoryRepository
import org.khjin.ecommerce.infrastructure.point.repository.PointRepository

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
        private val db = mutableMapOf<Long, PointEntity>()
        override fun findByCustomerId(customerId: Long): PointEntity? {
            return db[customerId]
        }

        override fun save(pointEntity: PointEntity): PointEntity {
            if(db[pointEntity.customerId] == null){
                db[pointEntity.customerId] = pointEntity
            }else {
                val old = db[pointEntity.customerId]!!
                val new = PointEntity(pointEntity.customerId, pointEntity.amount + old.amount)
                db[pointEntity.customerId] = new
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