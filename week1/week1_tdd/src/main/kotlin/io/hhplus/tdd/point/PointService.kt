package io.hhplus.tdd.point

import io.hhplus.tdd.database.UserPointTable
import io.hhplus.tdd.exceptions.InsufficientPointException
import io.hhplus.tdd.exceptions.InvalidPointException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PointService @Autowired constructor(
    val userPointTable: UserPointTable,
    val pointHistoryService: PointHistoryService
) {

    @Synchronized
    fun chargeUserPoint(id: Long, amount: Long): UserPoint {

        /*
            event queue에 등록
               => event processor에서 queue에 있는
         */

        if(amount <= 0){
            throw InvalidPointException("충전할 포인트는 양수만 입력 가능합니다.")
        }

        pointHistoryService.insertPointChargeHistory(id, amount)
        val result = userPointTable.insertOrUpdate(id, amount + retrieveUserPoint(id).point)

        return result
    }

    fun retrieveUserPoint(id: Long): UserPoint {
        return userPointTable.selectById(id)
    }

    @Synchronized
    fun useUserPoint(id: Long, amount: Long): UserPoint {
        if(amount <= 0){
            throw InvalidPointException("사용할 포인트는 양수만 입력 가능합니다.")
        }

        val currentPoint = retrieveUserPoint(id).point

        if(currentPoint < amount){
            throw InsufficientPointException("사용할 포인트가 현재 보유하고 있는 포인트보다 많습니다.")
        }

        pointHistoryService.insertPointUseHistory(id, amount)
        val result = userPointTable.insertOrUpdate(id, currentPoint - amount)

        return result
    }

}
