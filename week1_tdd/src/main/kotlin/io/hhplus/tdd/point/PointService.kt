package io.hhplus.tdd.point

import io.hhplus.tdd.database.UserPointTable
import io.hhplus.tdd.exceptions.InvalidPointException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PointService @Autowired constructor(
    val userPointTable: UserPointTable,
    val pointHistoryService: PointHistoryService
) {
    fun chargeUserPoint(id: Long, amount: Long): UserPoint {

        if( amount <= 0 ){
            throw InvalidPointException("충전할 포인트는 양수만 입력 가능합니다.")
        }

        pointHistoryService.insertPointChargeHistory(id, amount)

        return userPointTable.insertOrUpdate(id, amount + retrieveUserPoint(id).point)
    }

    fun retrieveUserPoint(id: Long): UserPoint {
        return userPointTable.selectById(id)
    }

}
