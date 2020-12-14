package com.wenance.challenge.vo

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.sql.Timestamp

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class TimestampRequest(
    val since: Timestamp?,
    @JsonProperty(required = false)
    val until: Timestamp?
)
