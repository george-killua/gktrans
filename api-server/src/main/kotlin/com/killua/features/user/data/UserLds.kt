package com.killua.features.user.data

import com.killua.extenstions.DbResult
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserInfoEntity
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserInfoDto
import com.killua.features.user.domain.model.UserType
import java.util.*

interface UserLds {
    suspend fun getAllUsers(companyId: UUID): DbResult<List<UserEntity>>
    suspend fun getAllUsers(): DbResult<List<UserEntity>>
    suspend fun searchUser(keyword: String): DbResult<List<UserEntity>>
    suspend fun getUser(userId: UUID): DbResult<UserEntity>
    suspend fun getUserLoginCredential(email: String, password: String): DbResult<UserEntity>
    suspend fun getUserInfo(userId: UUID): DbResult<UserInfoEntity>
    suspend fun addUser(user: UserDto, currentUser: UserEntity): DbResult<UserEntity>
     fun addUserTest(user: UserDto): DbResult<UserEntity>
    suspend fun addUserInfo(userId: UUID, userInfo: UserInfoDto, currentUser: UserEntity): DbResult<UserInfoEntity>
    suspend fun updateUserEmail(userId: UUID, email: String, currentUser: UserEntity): DbResult<UserEntity>
    suspend fun updateUserPassword(userId: UUID, password: String, currentUser: UserEntity): DbResult<UserEntity>
    suspend fun updateUserType(userId: UUID, userType: UserType, currentUser: UserEntity): DbResult<UserEntity>
    suspend fun updateUserInfo(userId: UUID, user: UserInfoDto, currentUser: UserEntity): DbResult<UserInfoEntity>
    suspend fun deleteUser(userId: UUID, currentUser: UserEntity): DbResult<Unit>
    suspend fun deleteUserInfo(userId: UUID, currentUser: UserEntity): DbResult<Unit>
    suspend fun cleanUsersTable(): DbResult<Int>
    suspend fun cleanUserInfoTable(): DbResult<Int>


}