package com.cqrs.cqrsexample.mvc.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.Instant.now
import java.util.*

private const val MSDN_CODE = "MSDN 2"
private const val USER_ATTRIBUTE = "some user attribute"
private const val USER_ELK_STACK_CODE = "ELK:USERS12"
private const val USER_TAG_METRIC = "users_counter"
private const val ANOTHER_PROPS = "Active"

@Service
class UserServiceImpl(
    private val userDao: UserDao,
    @Qualifier("mailNotificationClient")
    private val mailNotificationClient: NotificationClient,
    @Qualifier("smsNotificationClient")
    private val smsNotificationClient: NotificationClient,
    @Qualifier("mmsNotificationClient")
    private val mmsNotificationClient: NotificationClient,
    private val userCacheAttribute: UserCacheAttribute,
    private val validationService: ValidationService,
    private val msdnHelper: MsdnHelper,
    private val userUtils: UserUtils,
    private val additionalUserService: AdditionalUserService,
    private val auditUserService: AuditUserService,
    private val userLogElkFacade: UserLogElkFacade,
    private val log: LoggerUtilService,
    private val eventPublisher: ApplicationEventPublisher,
    private val userMetricsFacade: UserMetricsFacade,
    private val andOneMoreUserHelper: AndOneMoreUserHelper,
    private val notEnoughNotificationClient: NotEnoughNotificationClient,
    private val thirdPartyServiceAdapter: ThirdPartyServiceAdapter
) : UserService<UserModel> {

    data class UserDetails(
        val userId: Long,
        val userName: String,
        val userAge: Int,
        val surName: String,
        val patrName: String,
        val married: Boolean,
        val children: Int
    )

    override fun makeUserPreferred(userId: Long) {
        TODO("Not yet implemented")
    }

    override fun getUser(userId: Long): UserModel? {
        TODO("Not yet implemented")
    }

    override fun getUsersWithName(name: String): Set<UserModel> {
        TODO("Not yet implemented")
    }

    override fun getPreferredUsers(): Set<UserModel> {
        TODO("Not yet implemented")
    }

    override fun changeUserLocale(userId: Long, locale: Locale) {
        userDao
        mailNotificationClient
        smsNotificationClient
        mmsNotificationClient
        userCacheAttribute
        validationService
        msdnHelper
        userUtils
        additionalUserService
        auditUserService
        userLogElkFacade
        log
        eventPublisher
        userMetricsFacade
        andOneMoreUserHelper
        notEnoughNotificationClient
        thirdPartyServiceAdapter
        MSDN_CODE
        USER_ATTRIBUTE
        USER_ELK_STACK_CODE
        USER_TAG_METRIC
        ANOTHER_PROPS
    }

    override fun findNonActiveUsers(): Set<UserModel> {
        TODO("Not yet implemented")
    }

    override fun findDisabledUsers(): Set<UserModel> {
        TODO("Not yet implemented")
    }

    override fun findUnmodifiableUsers(): Set<UserModel> {
        TODO("Not yet implemented")
    }

    override fun userRideBicycle(user: UserModel) {
        userMetricsFacade.counter(USER_TAG_METRIC, "userRideBicycle").increment()
        userMetricsFacade.timer(USER_TAG_METRIC) {
            try {
                log.debug("Process userRideBicycle for user: {}", user)
                if (!validationService.isValid(user)) {
                    log.warn("Given user is invalid {}", user)
                    throw IllegalArgumentException("User: $user is invalid")
                }

                if (getUser(user.id) == null) {
                    log.error("User with id: {} not found", user)
                    throw UserNotFoundException("User with id: ${user.id} not found")
                }

                auditUserService.auditLog(user, now(), "change state ride bicycle")

                userDao.setUserRideBicycleFlag(user)
                mailNotificationClient.send(user, "cool, you ride bicycle")

                eventPublisher.publishEvent(UserRideBycicleEvent(user))
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

    override fun makeUsersUnmodifiable(users: Set<UserModel>) {
        TODO("Not yet implemented")
    }

    override fun setStatusDisabled(user: UserModel) {
        TODO("Not yet implemented")
    }

    override fun notifyUsersAboutSales(users: Set<UserModel>) {
        TODO("Not yet implemented")
    }

    override fun changeStatusOnActive(user: UserModel) {
        TODO("Not yet implemented")
    }

    override fun removeUser(user: UserModel) {
        TODO("Not yet implemented")
    }

    override fun notifyUserAboutChanges(user: UserModel) {
        TODO("Not yet implemented")
    }

    override fun editUserDetails(userDetails: UserModel) {
        TODO("Not yet implemented")
    }

    override fun createUser(user: UserModel) {
        TODO("Not yet implemented")
    }

}

fun Any.increment() {
    TODO("Not yet implemented")
}
