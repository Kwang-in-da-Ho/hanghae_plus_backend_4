package io.hhplus.tdd

import io.hhplus.tdd.point.UsePoint
import io.hhplus.tdd.point.UserPointService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import java.util.concurrent.CompletableFuture

// 통합테스트 관심사
// - 동시성 제어, DB 정합성, 실제 조립했을 때 잘 수행되는지
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class ConcurrencyTest(
    private val userPointService: UserPointService
) {

    @Test
    fun `동시에 4번 입금 요청ㄱㄱ`() {
        // setup
        val userId = 1L
        // execute
        CompletableFuture.allOf(
            CompletableFuture.runAsync { userPointService.charge(UsePoint(userId, 100)) },
            CompletableFuture.runAsync { userPointService.charge(UsePoint(userId, 400)) },
            CompletableFuture.runAsync { userPointService.charge(UsePoint(userId, 300)) },
            CompletableFuture.runAsync { userPointService.charge(UsePoint(userId, 200)) },
        ).join()
        // assert
        val result = userPointService.read(userId)
        assertThat(result.point).isEqualTo(100 + 400 + 300 + 200)
    }

    @Test
    fun `400원, -500원 이 오면 어떻게든 어떤 예외가 터져야함`() {

        CompletableFuture.allOf(
            CompletableFuture.runAsync { userPointService.charge(UsePoint(1, 400)) },
            CompletableFuture.runAsync { userPointService.use(1, 500) },
        )
            .exceptionally { e ->
                checkNotNull(e.cause as? RuntimeException)
                null
            }
            .join()
    }
}