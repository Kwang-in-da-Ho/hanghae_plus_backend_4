package io.hhplus.tdd.point

import io.hhplus.tdd.database.PointHistoryTable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PointHistoryService @Autowired constructor(
    private val pointHistoryTable: PointHistoryTable
){
    fun retrieveUserPointHistoryList(id: Long): List<PointHistory> {
        return pointHistoryTable.selectAllByUserId(id)
    }

    fun insertPointChargeHistory(id: Long, amount: Long): PointHistory {
        return pointHistoryTable.insert(id, amount, TransactionType.CHARGE, System.currentTimeMillis())
    }

    fun insertPointUseHistory(id: Long, amount: Long): PointHistory {
        return pointHistoryTable.insert(id, amount, TransactionType.USE, System.currentTimeMillis())
    }

}
