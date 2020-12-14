package com.wenance.challenge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = ["com.wenance", "com.wenance.challenge"])
class BitcoinServiceApplication

fun main(args: Array<String>) {
	runApplication<BitcoinServiceApplication>(*args)
}
