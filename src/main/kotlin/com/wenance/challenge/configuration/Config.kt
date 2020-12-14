package com.wenance.challenge.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.client.RestTemplate

@Configuration
@EnableScheduling
class Config {
    @Bean
    fun getRestTemplate(): RestTemplate {
        var mappingJackson = MappingJackson2HttpMessageConverter()
        mappingJackson.supportedMediaTypes =
                listOf(MediaType.ALL)
        var restTemplate = RestTemplate()
                restTemplate.messageConverters.add(mappingJackson)
        return restTemplate
    }
}