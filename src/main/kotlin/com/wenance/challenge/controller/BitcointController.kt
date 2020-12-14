package com.wenance.challenge.controller

import com.wenance.challenge.service.BitcointService
import com.wenance.challenge.vo.PriceResponse
import com.wenance.challenge.vo.PriceStatisticsResponse
import com.wenance.challenge.vo.TimestampRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.sql.Timestamp

/*
1) Realice una llamada recurrente cada 10 segundos, almacene los datos y que
exponga a través de un API REST las siguientes funcionalidades
         a) Obtener el precio del Bitcoin en cierto timestamp.
         b) Conocer el promedio de valor entre dos timestamps así como la
diferencia porcentual entre ese valor promedio y el valor máximo
almacenado para toda la serie tem poral disponible.
         c) Devolver en forma paginada los datos almacenados con o sin filtro de
timestamp. (Punto Extras)
*/

@RestController
@RequestMapping("/v1/bitcoin")
class BitcointController {

    @Autowired
    lateinit var bitcointService: BitcointService

    @GetMapping("/alive")
    fun alive(): ResponseEntity<String>{
        return ResponseEntity.ok("Service is alive!")
    }

    @PostMapping("/price/timestamp", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun priceWithTimestamp(@RequestBody timestampRequest: TimestampRequest): PriceResponse {
        return bitcointService.price(timestampRequest.since!!)
    }

    @PostMapping("/price/statistics", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun priceStatistics(@RequestBody timestampRequest: TimestampRequest): PriceStatisticsResponse {

        return bitcointService.statistics(timestampRequest.since!!, timestampRequest.until!!)
    }

    @PostMapping("/price/statistics2", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun priceStatistics2(@RequestBody timestampRequest: TimestampRequest): List<PriceStatisticsResponse>{
        return bitcointService.statistics2(timestampRequest.since!!, timestampRequest.until!!)
    }

    /*@PostMapping("/statistics/{page}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun priceStatistics(@PathVariable page: Int, @RequestBody timestampRequest: TimestampRequest): PriceStatisticsResponse {
        var since = timestampRequest.since
        var until = timestampRequest.until

        val prices = bitcointRepository.pricePagedFor(since, until, page)

        return bitcointService.statistics(prices)
    }
     */
}