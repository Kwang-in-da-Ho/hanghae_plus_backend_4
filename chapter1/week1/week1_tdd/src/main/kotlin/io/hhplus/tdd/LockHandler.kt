package io.hhplus.tdd

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class LockHandler {

    private val lockMap = ConcurrentHashMap<Long, Lock>() //사용자별로 lock을 걸기 위함

    fun<T> executeOnLock(key: Long, block: () -> T): T{
        val lock = lockMap.computeIfAbsent(key) { ReentrantLock() }
        val acquired = lock.tryLock(5, TimeUnit.SECONDS)
        if(!acquired) throw RuntimeException("Transaction Timeout")

        return block.invoke()
    }

}