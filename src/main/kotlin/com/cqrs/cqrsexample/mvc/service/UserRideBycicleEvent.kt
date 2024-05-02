package com.cqrs.cqrsexample.mvc.service

import org.springframework.context.ApplicationEvent

class UserRideBycicleEvent(user: UserModel) : ApplicationEvent() {

}
