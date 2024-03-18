package io.hhplus.tdd.point

import io.hhplus.tdd.TddApplication
import io.hhplus.tdd.exceptions.InvalidPointException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [TddApplication::class])
class PointServiceTest @Autowired constructor(
    private val pointService: PointService
){

    @Test
    @DisplayName("양수인 포인트만 충전 가능하다")
    fun `points given to be charged should be a positive number`() {
        //negative number
        assertThrows<InvalidPointException> {
            pointService.chargeUserPoint(1L, -123)
        }

        //zero
        assertThrows<InvalidPointException> {
            pointService.chargeUserPoint(1L, 0)
        }

    }


}