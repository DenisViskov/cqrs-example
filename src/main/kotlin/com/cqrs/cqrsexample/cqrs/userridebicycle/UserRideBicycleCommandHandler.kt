package com.cqrs.cqrsexample.cqrs.userridebicycle

import com.cqrs.cqrsexample.mvc.service.*
import com.trendyol.kediatr.Command
import com.trendyol.kediatr.CommandHandler
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import java.time.Instant


data class UserRideBicycleCommand(
    val user: UserModel
) : Command

@Component
class UserRideBicycleCommandHandler(
    private val userMetricsFacade: UserMetricsFacade,
    private val log: LoggerUtilService,
    private val validationService: ValidationService,
    private val auditUserService: AuditUserService,
    private val userDao: UserDao,
    @Qualifier("mailNotificationClient")
    private val mailNotificationClient: NotificationClient,
    private val eventPublisher: ApplicationEventPublisher,
    private val userService: UserService<UserModel>
) : CommandHandler<UserRideBicycleCommand> {
    override suspend fun handle(command: UserRideBicycleCommand) {
        userMetricsFacade.counter(USER_TAG_METRIC, "userRideBicycle").increment()
        userMetricsFacade.timer(USER_TAG_METRIC) {
            try {
                log.debug("Process userRideBicycle for user: {}", command.user)
                if (!validationService.isValid(command.user)) {
                    log.warn("Given user is invalid {}", command.user)
                    throw IllegalArgumentException("User: $command.user is invalid")
                }

                if (userService.getUser(command.user.id) == null) {
                    log.error("User with id: {} not found", command.user)
                    throw UserNotFoundException("User with id: ${command.user.id} not found")
                }

                auditUserService.auditLog(command.user, Instant.now(), "change state ride bicycle")

                userDao.setUserRideBicycleFlag(command.user)
                mailNotificationClient.send(command.user, "cool, you ride bicycle")

                eventPublisher.publishEvent(UserRideBycicleEvent(command.user))
                log.debug("Process userRideBicycle success")
            } catch (ex: DataAccessException) {
                log.error("Couldn't save user state due to: {}", ex.message, ex)
                throw ex
            } catch (ex: SmtpException) {
                log.error("Couldn't send email notification due to: {}", ex.message, ex)
                throw ex
            } catch (ex: Exception) {
                log.error("Unexpected error occurred due to: {}", ex.message, ex)
                throw ex
            }
        }
    }
}

private const val USER_TAG_METRIC = "users_counter"