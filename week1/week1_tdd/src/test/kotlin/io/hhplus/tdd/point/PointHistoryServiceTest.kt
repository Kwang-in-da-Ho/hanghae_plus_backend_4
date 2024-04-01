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

    @Test
    @DisplayName("포인트 이력을 조회하면 각 충전 또는 사용 내역이 발생한 순서대로 표시된다")
    fun `history data should show each charge or use event in order it happened`() {
        val testUser = 2L
        val typeList = listOf(
            TransactionType.CHARGE,
            TransactionType.CHARGE,
            TransactionType.USE,
            TransactionType.USE,
            TransactionType.USE,
            TransactionType.CHARGE)
        val amountList = listOf(
            1000L,
            123515L,
            34412L,
            42345L,
            2222L,
            12405L,
        )

        for(i in typeList.indices){
            when(typeList[i]){
                TransactionType.CHARGE -> pointService.chargeUserPoint(testUser, amountList[i])
                TransactionType.USE -> pointService.useUserPoint(testUser, amountList[i])
            }
        }

        val userPointHistoryList = pointHistoryService.retrieveUserPointHistoryList(testUser)

        assertEquals(typeList.size, userPointHistoryList.size)

        for(i in userPointHistoryList.indices){
            assertEquals(typeList[i], userPointHistoryList[i].type)
            assertEquals(amountList[i], userPointHistoryList[i].amount)
        }
    }

    @Test
    @DisplayName("포인트 충전이나 사용을 한 적이 없는 사용자에 대해 empty list가 반환된다")
    fun `for users who have never charged or used points, an empty list should be returned`() {
        val testUser = 1234L
        assert(pointHistoryService.retrieveUserPointHistoryList(testUser).isEmpty())
    }
}