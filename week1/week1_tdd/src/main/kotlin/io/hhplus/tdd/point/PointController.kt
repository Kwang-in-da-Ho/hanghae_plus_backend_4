package io.hhplus.tdd.point

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/point")
class PointController @Autowired constructor(
    private val pointService: PointService,
    private val pointHistoryService: PointHistoryService
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("{id}")
    fun point(
        @PathVariable id: Long,
    ): UserPoint {
        return pointService.retrieveUserPoint(id)
    }

    @GetMapping("{id}/histories")
    fun history(
        @PathVariable id: Long,
    ): List<PointHistory> {
        return pointHistoryService.retrieveUserPointHistoryList(id)
    }

    @PatchMapping("{id}/charge")
    fun charge(
        @PathVariable id: Long,
        @RequestBody amount: Long, //DTO로 받지 않고 이렇게 받아도 안전한가? 현업에서도 이런 경우가 있나..?
    ): UserPoint {
        return pointService.chargeUserPoint(id, amount)
    }

    @PatchMapping("{id}/use")
    fun use(
        @PathVariable id: Long,
        @RequestBody amount: Long,
    ): UserPoint {
        return pointService.useUserPoint(id, amount)
    }
}