package com.killua.features.user.domain

import com.killua.extenstions.toUuid
import com.killua.features.user.data.UserLocalDataSource
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.domain.mapper.toUserDto
import com.killua.features.user.domain.mapper.toUserInfo
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserInfoDto
import com.killua.features.user.domain.model.UserType

class UserRepositoryImpl(private val userLds: UserLocalDataSource) : UserRepository {
    override suspend fun getAllUsers(companyId: String): List<UserDto> {
        return userLds.getAllUsers(companyId.toUuid()).map { it.toUserDto() }
    }

    override suspend fun getAllUsers(): List<UserDto> {
        return userLds.getAllUsers().map { it.toUserDto() }
    }

    override suspend fun getUser(userId: String): UserDto? {
        return userLds.getUser(userId.toUuid())?.toUserDto()
    }

    override suspend fun getUserInfo(userId: String): UserInfoDto? {
        return userLds.getUser(userId.toUuid())?.userInfo?.toUserInfo()
    }

    override suspend fun getUserLoginCredential(email: String, password: String): UserEntity? {
        return getUserLoginCredential(email, password)
    }

    override suspend fun addUser(user: UserDto, currentUser: UserEntity): UserDto {
        return userLds.addUser(user, currentUser).toUserDto()
    }

    override suspend fun addUserInfo(userID: String, userInfo: UserInfoDto, currentUser: UserEntity): UserInfoDto? {
        return userLds.addUserInfo(userID.toUuid(), userInfo, currentUser)?.toUserInfo()
    }
    override suspend fun updateUserEmail(userId: String, email: String, currentUser: UserEntity): UserDto? {
        return userLds.updateUserEmail(userId.toUuid(), email, currentUser)?.toUserDto()
    }

    override suspend fun updateUserPassword(userId: String, password: String, currentUser: UserEntity): UserDto? {
        return userLds.updateUserPassword(userId.toUuid(), password, currentUser)?.toUserDto()
    }

    override suspend fun updateUserType(userId: String, userType: UserType, currentUser: UserEntity): UserDto? {
        return userLds.updateUserType(userId.toUuid(), userType, currentUser)?.toUserDto()
    }

    override suspend fun updateUserInfo(userId: String, user: UserInfoDto, currentUser: UserEntity): UserInfoDto? {
        return userLds.updateUserInfo(userId.toUuid(), user, currentUser)?.toUserInfo()
    }

    override suspend fun deleteUser(userId: String, currentUser: UserEntity) {
        return userLds.deleteUser(userId.toUuid(), currentUser)
    }

    override suspend fun deleteUserInfo(userId: String, currentUser: UserEntity) {
        return userLds.deleteUserInfo(userId.toUuid(), currentUser)
    }

    override suspend fun cleanUsersTable(): Int {
        return userLds.cleanUsersTable()
    }

    override suspend fun cleanUserInfoTable(): Int {
      return  userLds.cleanUserInfoTable()
    }
}