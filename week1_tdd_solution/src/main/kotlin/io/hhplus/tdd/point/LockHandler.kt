package io.hhplus.tdd.point

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

@Component
class LockHandler {
    // userId 기반으로 Lock 을 관리하는 ConcurrentHashMap
    private val lockMap = ConcurrentHashMap<Long, Lock>()

    fun <T> executeOnLock(key: Long, block: () -> T): T {
        val lock = lockMap.computeIfAbsent(key) { _ -> ReentrantLock() }
        val acquired = lock.tryLock(5, TimeUnit.SECONDS)
        if (!acquired) throw RuntimeException("Timeout 에러 발생")

        // 락을 획득
        try {
            return block()
        } finally {
            lock.unlock()
        }
    }
}