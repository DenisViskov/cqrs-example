package com.cqrs.cqrsexample.mvc.service

import org.springframework.stereotype.Component

@Component
class UserMetricsFacade {
    fun counter(userTagMetric: String, s: String): Any {
        TODO("Not yet implemented")
    }

    fun timer(userTagMetric: String, function: () -> Unit) {
        TODO("Not yet implemented")
    }

}
