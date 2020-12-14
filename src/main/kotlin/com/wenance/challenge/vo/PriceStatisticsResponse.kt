package com.wenance.challenge.vo

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class PriceStatisticsResponse(
    val promedio: Double,
    val percentDiff: Double
)
