package com.killua.features.user.data

import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserInfoEntity
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserInfoDto
import java.util.*

interface UserLocalDataSource {
    suspend fun getAllUsers(companyId: UUID): List<UserEntity>
    suspend fun getUser(uuid: UUID): UserEntity?
    suspend fun getUserLoginCredential(email: String, password: String): UserEntity?
    suspend fun addUser(user: UserDto): UserEntity
    suspend fun addUserInfo(user: UserInfoDto): UserInfoEntity
    suspend fun updateUser(user: UserDto, currentUser: UserEntity): UserEntity?
    suspend fun updateUserInfo(user: UserInfoDto, currentUser: UserEntity): UserInfoEntity?
    suspend fun deleteUser(userId: UUID): Boolean
    suspend fun deleteUserInfo(userId: UUID): Boolean

}