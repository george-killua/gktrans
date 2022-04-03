package com.killua.features.user.domain

import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserUpdateDetails

interface UserRepository {
    suspend fun getAllUsers(): List<UserDto>
    suspend fun createUser(user: UserDto): UserDto
    suspend fun getUser(uuid: String): UserDto?
    suspend fun getUser(email: String,password:String): UserDto?
    suspend fun removeImage(userId: String): Boolean?
    suspend fun removeUser(userId: String): Boolean

}

