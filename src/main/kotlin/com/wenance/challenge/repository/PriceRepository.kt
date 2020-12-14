package com.wenance.challenge.repository

import com.fasterxml.jackson.databind.ser.std.StringSerializer
import com.wenance.challenge.model.PriceStatistics
import com.wenance.challenge.service.BitcointService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.sql.Timestamp
import java.text.SimpleDateFormat

@Repository
class PriceRepository {

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
        val logger = LoggerFactory.getLogger(PriceRepository::class.java)
    }

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String,PriceStatistics>

    private fun setSerializeString(){
        redisTemplate.keySerializer = StringRedisSerializer()
    }

    private fun setSerializeDefault(){
        redisTemplate.keySerializer = JdkSerializationRedisSerializer()
    }

    fun add(priceStatistics: PriceStatistics){
        val dateFormatString = SimpleDateFormat(DATE_FORMAT).format(priceStatistics.getDateTimestamp())
        logger.info("Save priceStatistics date: {}", dateFormatString)
        redisTemplate.opsForValue().set(dateFormatString, priceStatistics)
        //redisTemplate.opsForValue().set("test", priceStatistics)
    }

    fun getBy(date: String): PriceStatistics {
        logger.info("Retrieve priceStatistics, date: {}", date)
        return redisTemplate.opsForValue().get(date)?.let { it } ?: PriceStatistics(BigDecimal(0), "1969/12/31 00:00:00")
    }

    fun getAllBy(since: Timestamp, until: Timestamp): MutableCollection<PriceStatistics>{
        logger.info("Retrieve priceStatistics, since {} until {}", since, until)

        //TODO remove me!
        setSerializeString()
        var keys = redisTemplate.keys("*2020-12-14*")
        var res = mutableListOf<PriceStatistics>()
        setSerializeDefault()

        if(keys != null){
            keys.forEach {it ->
                var price: PriceStatistics = redisTemplate.opsForValue()[it] ?: return@forEach
                if(price.getDateTimestamp().after(since) && price.getDateTimestamp().before(until))
                    res.add(price)
            }
        }

        return res
    }
}