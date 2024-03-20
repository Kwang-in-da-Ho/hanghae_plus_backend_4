package io.hhplus.tdd.point

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class ConcurrencyTest @Autowired constructor(
    private val pointService: PointService
){

    @Test
    fun `test concurrency for user point charge`() {
        val amountList = (100L..1000L step 100).toList()

        val threadCount = amountList.size
        val testUser = 0L

        val executorService = Executors.newFixedThreadPool(threadCount)
        val latch = CountDownLatch(threadCount)

        for (i in 0..< threadCount) {
            executorService.submit {
                kotlin.runCatching {
                    // 충전 메소드 호출
                    pointService.chargeUserPoint(testUser, amountList[i])
                }.also {
                    latch.countDown()
                }
            }
        }
        latch.await()
        // 모두 완료된 후에 300포인트가 충전된 것을 확인해야 한다. (가장 중요)
        assertEquals(pointService.retrieveUserPoint(testUser).point, amountList.sum())
    }
}