package com.cqrs.cqrsexample.mvc.service

import org.springframework.stereotype.Component
import java.time.Instant

@Component
class AuditUserService {
    fun auditLog(user: UserModel, now: Instant?, s: String) {
        TODO("Not yet implemented")
    }

}
