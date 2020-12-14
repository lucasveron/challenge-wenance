package com.wenance.challenge.error

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorField(
        val code: String,
        val message: String
)