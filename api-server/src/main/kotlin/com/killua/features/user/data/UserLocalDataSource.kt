package com.killua.features.user.data

import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserInfoEntity
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserInfoDto
import com.killua.features.user.domain.model.UserType
import java.util.*

interface UserLocalDataSource {
    suspend fun getAllUsers(companyId: UUID): List<UserEntity>
    suspend fun getAllUsers(): List<UserEntity>
    suspend fun getUser(userId: UUID): UserEntity?
    suspend fun getUserInfo(userId: UUID): UserInfoEntity?
    suspend fun getUserLoginCredential(email: String, password: String): UserEntity?
     fun getUserLoginCredentiale(email: String, password: String): UserEntity?
    suspend fun addUser(user: UserDto, currentUser: UserEntity): UserEntity
     fun addUserTest(user: UserDto): UserEntity
    suspend fun addUserInfo(userId: UUID, userInfo: UserInfoDto, currentUser: UserEntity): UserInfoEntity?
    suspend fun updateUserEmail(userId: UUID, email: String, currentUser: UserEntity): UserEntity?
    suspend fun updateUserPassword(userId: UUID, password: String, currentUser: UserEntity): UserEntity?
    suspend fun updateUserType(userId: UUID, userType: UserType, currentUser: UserEntity): UserEntity?
    suspend fun updateUserInfo(userId: UUID, user: UserInfoDto, currentUser: UserEntity): UserInfoEntity?
    suspend fun deleteUser(userId: UUID, currentUser: UserEntity)
    suspend fun deleteUserInfo(userId: UUID, currentUser: UserEntity)
    suspend fun cleanUsersTable(): Int
    suspend fun cleanUserInfoTable(): Int


}