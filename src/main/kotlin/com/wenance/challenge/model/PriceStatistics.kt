package com.wenance.challenge.model

import java.io.Serializable
import java.math.BigDecimal
import java.sql.Timestamp

class PriceStatistics(price: BigDecimal, date: String): Serializable{
    val price = price
    val date = date

    fun getDateTimestamp(): Timestamp {
        return Timestamp.valueOf(date)
    }
}