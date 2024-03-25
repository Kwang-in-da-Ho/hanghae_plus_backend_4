package io.hhplus.tdd

import io.hhplus.tdd.point.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchException
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

class TestPointRepository : PointRepository {
    private val points = mutableMapOf<Long, UserPoint>()

    override fun read(userId: Long): UserPoint {
        return points.getOrPut(userId) { UserPoint(userId, 0, System.currentTimeMillis()) }
    }

    override fun modify(userId: Long, amount: Long): UserPoint {
        val newData = UserPoint(userId, amount, System.currentTimeMillis())
        points[userId] = newData
        return newData
    }
}

// 테스팅 Mock 라이브러리 뭐 쓰세요 ?
// 1. 테스팅 프레임워크 ( junit, jest, mocha )
// 2. Assert 라이브러리 ( assertj ) -> 일반 테스팅 프레임워크가 제공해주는 검증도구가 너무 미비하다.
// 3. Mock 라이브러리
// 4. 픽스쳐 라이브러리 ( instancio, fixture-monkey ) -> 테스팅용 데이터 가짜로 만들어주는 애

class UnitTest {
    @Test
    fun `충전 - 금액이 음수가 들어오면 터져야 한다`() {
        // setup
        val stub = TestPointRepository()
        stub.modify(1, 0)
        val sut = UserPointManager(UserPointReader(stub), UserPointWriter(stub))
        // execute
        val expected = catchException {
            sut.charge(1, -100)
        } as RuntimeException
        // assert
        assertThat(expected.message).isEqualTo("나가")
    }

    @Test
    fun `사용 - 보유금보다 많으면 터져야 한다`() {
        // setup
        val stub = TestPointRepository()
        stub.modify(1, 500)
        val sut = UserPointManager(UserPointReader(stub), UserPointWriter(stub))
        // execute
        val expected = catchException {
            sut.use(1, 1000)
        } as RuntimeException
        // assert
        assertThat(expected.message).isEqualTo("거지")
    }
}