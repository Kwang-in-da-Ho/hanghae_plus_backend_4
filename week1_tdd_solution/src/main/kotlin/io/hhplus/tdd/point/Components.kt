package io.hhplus.tdd.point

import org.springframework.stereotype.Component

@Component
class UserPointReader(private val repository: PointRepository) {
    fun read(userId: Long): UserPoint = repository.read(userId)
}

@Component
class UserPointWriter(private val repository: PointRepository) {
    fun modify(userId: Long, amount: Long): UserPoint = repository.modify(userId, amount)
}

@Component
class UserPointManager(
    private val userPointReader: UserPointReader,
    private val userPointWriter: UserPointWriter,
) {
    // 금액을 충전해서 저장한다.
    fun charge(userId: Long, amount: Long): UserPoint {
        if (amount < 0) throw RuntimeException("나가")
        val userPoint = userPointReader.read(userId)
        return userPointWriter.modify(userId, userPoint.point + amount)
    }

    // 금액을 사용한다.
    fun use(userId: Long, amount: Long): UserPoint {
        if (amount < 0) throw RuntimeException("나가")
        val userPoint = userPointReader.read(userId)
        // 음수가 될 수 없다.
        if (userPoint.point - amount < 0) throw RuntimeException("거지")
        return userPointWriter.modify(userId, userPoint.point - amount)
    }
}

data class UsePoint(val userId: Long, val amount: Long)
