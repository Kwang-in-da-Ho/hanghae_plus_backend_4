package io.hhplus.tdd.point

import org.springframework.stereotype.Service


@Service
class UserPointService(
    private val lockHandler: LockHandler,
    private val userPointReader: UserPointReader,
    private val userPointManager: UserPointManager,
) {
    fun charge(usePoint: UsePoint): UserPoint {
        return lockHandler.executeOnLock(usePoint.userId) {
            userPointManager.charge(usePoint.userId, usePoint.amount)
        }
    }

    fun use(userId: Long, amount: Long): UserPoint {
        return lockHandler.executeOnLock(userId) {
            userPointManager.use(userId, amount)
        }
    }

    fun read(userId: Long): UserPoint {
        return userPointReader.read(userId)
    }
}


