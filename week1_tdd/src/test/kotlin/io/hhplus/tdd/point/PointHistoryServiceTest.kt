package io.hhplus.tdd.point

import io.hhplus.tdd.TddApplication
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


@SpringBootTest(classes = [TddApplication::class])
class PointHistoryServiceTest @Autowired constructor(
    private val pointService: PointService,
    private val pointHistoryService: PointHistoryService
) {

    @Test
    @DisplayName("사용자가 포인트를 충전하면 충전 이력 데이터가 등록된다")
    fun `when a user charges points, history data should be registered`() {
        val chargePointList = listOf(1000L, 9999L, 300L)
        val testUser = 0L

        for (charge in chargePointList){
            pointService.chargeUserPoint(testUser, charge)
        }

        val userChargeHistoryList = pointHistoryService.retrieveUserPointHistoryList(testUser)

        assertEquals(userChargeHistoryList.size, chargePointList.size)

        for(i in userChargeHistoryList.indices){
            assertEquals(userChargeHistoryList[i].type, TransactionType.CHARGE)
            assertEquals(chargePointList[i], userChargeHistoryList[i].amount)
        }
    }

    @Test
    @DisplayName("사용자가 포인트를 사용하면 사용 이력 데이터가 등록된다")
    fun `when a user uses points, history data should be registered`() {
        val testUser = 1L

        //initial charge
        pointService.chargeUserPoint(testUser, 10000L)

        val usePointList = listOf(5123L, 432L, 865L, 53L, 2034L)

        for (use in usePointList){
            pointService.useUserPoint(testUser, use)
        }

        val userUseHistoryList = pointHistoryService.retrieveUserPointHistoryList(testUser)

        assertEquals(userUseHistoryList.size, usePointList.size + 1)

        println(userUseHistoryList)

        for(i in userUseHistoryList.indices){
            if(i == 0) continue //skip charge history
            assertEquals(userUseHistoryList[i].type, TransactionType.USE)
            assertEquals(usePointList[i-1], userUseHistoryList[i].amount)
        }
    }
}