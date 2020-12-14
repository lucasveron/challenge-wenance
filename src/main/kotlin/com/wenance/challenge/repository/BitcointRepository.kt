package com.wenance.challenge.repository

import com.wenance.challenge.model.PriceStatistics
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.sql.Timestamp

@Repository
class BitcointRepository {

    private fun allPrice(): Collection<PriceStatistics>{
        return listOf(
                PriceStatistics(1.toBigDecimal(), "2020-12-01 10:00:00"),
                PriceStatistics(2.toBigDecimal(), "2020-12-01 11:00:10"),
                PriceStatistics(3.toBigDecimal(), "2020-12-01 12:00:20"),
                PriceStatistics(2.toBigDecimal(), "2020-12-05 10:00:30"),
                PriceStatistics(1.toBigDecimal(), "2020-12-05 10:00:00"),
                PriceStatistics(3.toBigDecimal(), "2020-12-06 11:00:10"),
                PriceStatistics(2.toBigDecimal(), "2020-12-09 12:00:20"),
                PriceStatistics(4.toBigDecimal(), "2020-12-09 03:00:00")
        )
    }

    fun priceFor(since: Timestamp, until: Timestamp): Collection<PriceStatistics> {
        return allPrice().filter { it.getDateTimestamp() >= since && it.getDateTimestamp() <= until }
    }

    fun price(timestamp: Timestamp): PriceStatistics {
        try {
            return allPrice().first { it.getDateTimestamp() == timestamp }
        } catch (ex: NoSuchElementException){
            return PriceStatistics(BigDecimal(0), "1969/12/31 00:00:00")
        }
    }

    /*
    fun pricePagedFor(since: Timestamp, until: Timestamp, page: Any): Collection<PriceStatistics> {

    }
     */

}
