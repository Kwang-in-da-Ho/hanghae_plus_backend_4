package io.hhplus.tdd.event

import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Component
class EventQueue {

    val queue = ConcurrentHashMap<UUID, Event>()

    fun add(event:Event){
        queue[event.eventId] = event
    }

    fun get(eventId: UUID): Event?{
        return queue.get(eventId)
    }

}

open class Event {
    val eventId = UUID.randomUUID()

}

class PointChargeEvent(): Event(){
    private var id: Long? = null
    private var amount: Long? = null
    constructor(id: Long, amount: Long):this(){
        this.id = id
        this.amount = amount
    }
}

class PointUseEvent(): Event() {
    private var id: Long? = null
    private var amount: Long? = null
    constructor(id: Long, amount: Long):this(){
        this.id = id
        this.amount = amount
    }
}
