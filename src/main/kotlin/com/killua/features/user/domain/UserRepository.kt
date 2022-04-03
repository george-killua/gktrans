package com.killua.features.user.domain

import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserInfoDto
import com.killua.features.user.domain.model.UserType

interface UserRepository {

    suspend fun getAllUsers(companyId: String): List<UserDto>
    suspend fun getAllUsers(): List<UserDto>
    suspend fun getUser(userId: String): UserDto?
    suspend fun getUserInfo(userId: String): UserInfoDto?
    suspend fun getUserLoginCredential(email: String, password: String): UserEntity?
    suspend fun addUser(user: UserDto, currentUser: UserEntity): UserDto
    suspend fun addUserInfo(userID: String, userInfo: UserInfoDto, currentUser: UserEntity): UserInfoDto?
    suspend fun updateUser(user: UserDto, currentUser: UserEntity): UserDto?
    suspend fun updateUserEmail(userId: String, email: String, currentUser: UserEntity): UserDto?
    suspend fun updateUserPassword(userId: String, password: String, currentUser: UserEntity): UserDto?
    suspend fun updateUserType(userId: String, userType: UserType, currentUser: UserEntity): UserDto?
    suspend fun updateUserInfo(user: UserInfoDto, currentUser: UserEntity): UserInfoDto?
    suspend fun deleteUser(userId: String, currentUser: UserEntity)
    suspend fun deleteUserInfo(userId: String, currentUser: UserEntity)
    suspend fun cleanUsersTable(): Int
    suspend fun cleanUserInfoTable(): Int
}

