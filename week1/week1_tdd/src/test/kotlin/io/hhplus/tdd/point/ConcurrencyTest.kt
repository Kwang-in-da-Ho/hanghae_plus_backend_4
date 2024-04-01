package io.hhplus.tdd.point

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CompletableFuture
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

    @Test
    fun `동시에 4번 입금 요청` () {
        //setup
        val testUser = 0L
        //execute

        //동시성을 주관하는 API
        CompletableFuture.allOf(
            CompletableFuture.runAsync{ pointService.chargeUserPoint(testUser, 100L) },
            CompletableFuture.runAsync{ pointService.chargeUserPoint(testUser, 200L) },
            CompletableFuture.runAsync{ pointService.chargeUserPoint(testUser, 300L) },
            CompletableFuture.runAsync{ pointService.chargeUserPoint(testUser, 400L) },
        ).join() //위의 작업들을 동시에 실행시켜준다

        //assert - 조회했을 때 위의 4 작업이 모두 완료되어있어야 함
        assertEquals( pointService.retrieveUserPoint(testUser), 100L + 200L + 300L + 400L )

    }

    @Test
    fun `400원 충전, 500원 사용이 동시에 오면 항상 실패한다` () {
        //setup
        val testUser = 0L

        //execute
        //동시성을 주관하는 API
        assert(
            CompletableFuture.allOf(
                CompletableFuture.runAsync{ pointService.chargeUserPoint(testUser, 100L) },
                CompletableFuture.runAsync{ pointService.useUserPoint(testUser, 200L) },
            )
                .isCompletedExceptionally
        )

    }
}