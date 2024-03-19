package io.hhplus.tdd.point

import io.hhplus.tdd.TddApplication
import io.hhplus.tdd.exceptions.InvalidPointException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [TddApplication::class])
class PointServiceTest @Autowired constructor(
    private val pointService: PointService,
    private val pointHistoryService: PointHistoryService
){

    private val testUser = 0L

    @Test
    @DisplayName("양수인 포인트만 충전 가능하다")
    fun `points given to be charged should be a positive number`() {
        //negative number
        assertThrows<InvalidPointException> {
            pointService.chargeUserPoint(testUser, -123)
        }

        //zero
        assertThrows<InvalidPointException> {
            pointService.chargeUserPoint(testUser, 0)
        }
    }

    @Test
    @DisplayName("사용자가 포인트를 충전하면 정확히 충전한 만큼 포인트가 증가한다")
    fun `when a user charges points, the user's points should increase by the charged amount`() {
        val chargeAmount = 1000L

        val beforeChargePointAmount = pointService.retrieveUserPoint(id = testUser).point
        val afterChargePointAmount = pointService.chargeUserPoint(testUser, chargeAmount).point

        assertEquals(beforeChargePointAmount + chargeAmount, afterChargePointAmount)
    }

    @Test
    @DisplayName("사용자가 포인트를 충전하면 이력 데이터가 등록된다")
    fun `when a user charges points, history data should be registered`() {
        val chargePointList = listOf(1000L, 9999L, 300L)

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
}