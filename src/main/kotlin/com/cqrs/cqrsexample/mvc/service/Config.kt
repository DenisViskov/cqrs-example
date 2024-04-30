package com.cqrs.cqrsexample.mvc.service

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {

    @Bean("mailNotificationClient")
    fun mailNotificationClient(): NotificationClient {
        return object : NotificationClient {}
    }

    @Bean("smsNotificationClient")
    fun smsNotificationClient(): NotificationClient {
        return object : NotificationClient {}
    }

    @Bean("mmsNotificationClient")
    fun mmsNotificationClient(): NotificationClient {
        return object : NotificationClient {}
    }
}
