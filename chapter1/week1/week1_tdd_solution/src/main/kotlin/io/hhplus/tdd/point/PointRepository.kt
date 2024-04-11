package io.hhplus.tdd.point

import io.hhplus.tdd.database.PointHistoryTable
import io.hhplus.tdd.database.UserPointTable
import org.springframework.stereotype.Component

interface PointRepository {
    fun read(userId: Long): UserPoint

    fun modify(userId: Long, amount: Long): UserPoint
}

@Component
class PointCoreRepository(
    private val userPointTable: UserPointTable,
    private val pointHistoryTable: PointHistoryTable,
) : PointRepository {
    override fun read(userId: Long): UserPoint {
        return userPointTable.selectById(userId)
    }

    override fun modify(userId: Long, amount: Long): UserPoint {
        return userPointTable.insertOrUpdate(userId, amount)
    }
}