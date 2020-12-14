package com.wenance.challenge.service

import com.wenance.challenge.model.PriceStatistics
import com.wenance.challenge.repository.BitcointRepository
import com.wenance.challenge.repository.PriceRepository
//import com.wenance.challenge.repository.PriceRepository2
import com.wenance.challenge.vo.BitcointServiceResponse
import com.wenance.challenge.vo.PriceResponse
import com.wenance.challenge.vo.PriceStatisticsResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.sql.Timestamp
import java.text.SimpleDateFormat

@Service
class BitcointService {

    companion object {
        val logger = LoggerFactory.getLogger(BitcointService::class.java)
        //const val DATE_FORMAT = "yyyy/MM/dd HH:mm:ss"
        const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }

    @Value("\${url.bitcoint}")
    private val urlBitcoint: String? = null

    @Autowired
    lateinit var restTemplate: RestTemplate

    @Autowired
    lateinit var bitcointRepository: BitcointRepository

    @Autowired
    lateinit var priceRepository: PriceRepository

    fun statistics(since: Timestamp, until: Timestamp): PriceStatisticsResponse {


        val priceStatistics = bitcointRepository.priceFor(since, until)

        val maxPrice = priceStatistics.map { it.price }.maxOrNull()
        if(maxPrice == null){
            return PriceStatisticsResponse(0.0 ,0.0)
        }

        val average = priceStatistics.map{ it.price.toDouble() }.average()

        return PriceStatisticsResponse(average, ((maxPrice.toDouble() - average)/maxPrice.toDouble()) * 100)

    }

    fun price(date: Timestamp): PriceResponse {
        val priceStatistics = priceRepository.getBy(
                SimpleDateFormat(DATE_FORMAT).format(date)
        )
        return PriceResponse(priceStatistics.price.toDouble())
    }

    fun statistics2(since: Timestamp, until: Timestamp): PriceStatisticsResponse {
        val priceStatistics = priceRepository.getAllBy(since, until)

        val maxPrice = priceStatistics.map { it.price }.maxOrNull()
        if(maxPrice == null){
            return PriceStatisticsResponse(0.0 ,0.0)
        }

        val average = priceStatistics.map{ it.price.toDouble() }.average()

        return PriceStatisticsResponse(average, ((maxPrice.toDouble() - average)/maxPrice.toDouble()) * 100)
    }

    @Scheduled(fixedDelay = 10000)
    private fun priceScheduler(){
        val currentTime = Timestamp(System.currentTimeMillis())
        val time = SimpleDateFormat(DATE_FORMAT).format(currentTime)
        var bitcointResponse = restTemplate.getForEntity(urlBitcoint!!, BitcointServiceResponse::class.java).body!!

        logger.info("Current time: {}, bitcoint price: {}, save PriceStatistics", time, bitcointResponse.lprice)

        priceRepository.add(PriceStatistics(bitcointResponse.lprice.toBigDecimal(), time))
    }
}
