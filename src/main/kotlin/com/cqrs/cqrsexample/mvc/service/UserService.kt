package com.cqrs.cqrsexample.mvc.service

import java.util.Locale

interface UserService<U> {

    fun makeUserPreferred(userId: Long)

    fun getUser(userId: Long): U?

    fun getUsersWithName(name: String): Set<U>

    fun getPreferredUsers(): Set<U>

    fun changeUserLocale(userId: Long, locale: Locale)

    fun createUser(user: U)

    fun editUserDetails(userDetails: U)

    fun notifyUserAboutChanges(user: U)

    fun removeUser(user: U)

    fun changeStatusOnActive(user: U)

    fun findNonActiveUsers(): Set<U>

    fun notifyUsersAboutSales(users: Set<U>)

    fun setStatusDisabled(user: U)

    fun findDisabledUsers(): Set<U>

    fun makeUsersUnmodifiable(users: Set<U>)

    fun findUnmodifiableUsers(): Set<U>

    // and many others methods ...
}
