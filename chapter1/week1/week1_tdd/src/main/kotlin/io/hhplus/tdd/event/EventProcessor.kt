package io.hhplus.tdd.event

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class EventProcessor @Autowired constructor(
    private val eventQueue: EventQueue
) {

    @Scheduled(fixedRate = 100L)
    fun process() {

        //이벤트 큐에서 poll하여 해당 이벤트 처리
    }

}