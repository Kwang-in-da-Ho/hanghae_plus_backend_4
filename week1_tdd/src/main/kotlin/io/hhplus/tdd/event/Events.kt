package io.hhplus.tdd.event

import io.hhplus.tdd.point.TransactionType
import java.util.*

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

class PointChargeHistoryRegisterEvent(): Event() {
    private var id: Long? = null
    private var amount: Long? = null
    private val type = TransactionType.CHARGE
    constructor(id: Long, amount: Long):this(){
        this.id = id
        this.amount = amount
    }
}

class PointUseHistoryRegisterEvent(): Event() {
    private var id: Long? = null
    private var amount: Long? = null
    private val type = TransactionType.USE
    constructor(id: Long, amount: Long):this(){
        this.id = id
        this.amount = amount
    }
}
