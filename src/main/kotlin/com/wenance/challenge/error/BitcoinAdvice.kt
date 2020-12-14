package com.wenance.challenge.error

import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.RuntimeException
import java.net.http.HttpHeaders

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class BitcoinAdvice: ResponseEntityExceptionHandler() {
    companion object {
        private val logger = LoggerFactory.getLogger(BitcoinAdvice::class.java)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun noSuchElementException(ex: NoSuchElementException, headers: HttpHeaders, status: HttpStatus,
    request: WebRequest): ResponseEntity<Any>{
        return ResponseEntity.badRequest().body(
                ErrorField("1","No existe precio para la fecha solicitada")
        )
    }

    @ExceptionHandler(RuntimeException::class)
    fun runTimeExceptionHandler(e: RuntimeException): ResponseEntity<Any> {
        logger.error("A RuntimeException occurred, {}", e)
        val errorResponse = ErrorField("2", "error interno")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}