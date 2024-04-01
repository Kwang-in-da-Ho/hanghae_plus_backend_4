package io.hhplus.tdd.point

import io.hhplus.tdd.TddApplication
import io.hhplus.tdd.exceptions.InsufficientPointException
import io.hhplus.tdd.exceptions.InvalidPointException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [TddApplication::class])
class PointServiceTest @Autowired constructor(
    private val pointService: PointService,
){

    private val testUser = 0L

    /*
     * 궁금한 점
     * 1)
     */

    // charge
    @Test
    @DisplayName("양수인 포인트만 충전 가능하다")
    fun `charge amount should be a positive number`() {
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
        val chargePointList = listOf(1000L, 2300L, 500L, 600L)

        for(charge in chargePointList){
            val beforeChargeAmount = pointService.retrieveUserPoint(id = testUser).point
            val afterChargeAmount = pointService.chargeUserPoint(testUser, charge).point
            assertEquals(beforeChargeAmount +  charge, afterChargeAmount)
        }
    }

    //use
    @Test
    @DisplayName("양수인 포인트만 충전 가능하다")
    fun `use amount should be a positive number`() {
        //negative number
        assertThrows<InvalidPointException> {
            pointService.useUserPoint(testUser, -1235123)
        }

        //zero
        assertThrows<InvalidPointException> {
            pointService.useUserPoint(testUser, 0)
        }
    }

    @Test
    @DisplayName("사용자는 자신이 보유한 포인트를 초과하는 양의 포인트를 사용할 수 없다")
    fun `a user cannot use more points than the amount the user possesses`() {
        val currentAmount = pointService.retrieveUserPoint(testUser).point
        val invalidUseAmount = currentAmount + 1L

        assertThrows<InsufficientPointException>{
            pointService.useUserPoint(testUser, invalidUseAmount)
        }
    }

    @Test
    @DisplayName("사용자가 포인트를 사용하면 정확히 사용한 만큼 포인트가 차감된다")
    fun `when a user uses points, the user's points should decrease by the used amount`() {
        val useList = listOf(1000L, 234L, 423L, 645L)

        //initial charge
        pointService.chargeUserPoint(testUser, 5000L).point

        for(use in useList){
            val beforeUseAmount = pointService.retrieveUserPoint(testUser).point
            val afterUseAmount = pointService.useUserPoint(testUser, use).point
            assertEquals(beforeUseAmount - use, afterUseAmount)
        }
    }
}