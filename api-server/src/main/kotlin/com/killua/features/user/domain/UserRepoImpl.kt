package com.killua.features.user.domain

import com.killua.extenstions.DatabaseExt.dbTransaction
import com.killua.extenstions.UserNotFoundException
import com.killua.extenstions.checkResult
import com.killua.extenstions.toUuid
import com.killua.features.user.data.UserLds
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.domain.mapper.toUserDto
import com.killua.features.user.domain.mapper.toUserInfo
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserInfoDto
import com.killua.features.user.domain.model.UserType
import kotlinx.coroutines.CoroutineDispatcher

class UserRepoImpl(private val userLds: UserLds, private val dispatcher: CoroutineDispatcher) : UsersRepo {
    override suspend fun getAllUsers(companyId: String): List<UserDto> {
        return dbTransaction(dispatcher) {
            userLds.getAllUsers(companyId.toUuid())
                .checkResult(UserNotFoundException("there is no users in this company")).map { it.toUserDto() }
        } ?: emptyList()
    }

    override suspend fun getAllUsers(): List<UserDto> {
        return dbTransaction(dispatcher) {
            userLds.getAllUsers().checkResult(UserNotFoundException()).map { it.toUserDto() }
        } ?: emptyList()
    }

    override suspend fun getUser(userId: String): UserDto? {
        return dbTransaction(dispatcher) {
            userLds.getUser(userId.toUuid()).checkResult(UserNotFoundException()).toUserDto()
        }
    }

    override suspend fun getUserInfo(userId: String): UserInfoDto? {
        return dbTransaction(dispatcher) {
            userLds.getUser(userId.toUuid()).checkResult(UserNotFoundException()).userInfo?.toUserInfo()
        }
    }

    override suspend fun getUserLoginCredential(email: String, password: String): UserEntity? {
        return dbTransaction(dispatcher) { getUserLoginCredential(email, password) }
    }

    override suspend fun addUser(user: UserDto, currentUser: UserEntity): UserDto? {
        return dbTransaction(dispatcher) {
            userLds.addUser(user, currentUser).checkResult(UserNotFoundException()).toUserDto()
        }
    }

    override suspend fun addUserInfo(userID: String, userInfo: UserInfoDto, currentUser: UserEntity): UserInfoDto? {
        return dbTransaction(dispatcher) {
            userLds.addUserInfo(userID.toUuid(), userInfo, currentUser).checkResult(UserNotFoundException())
                .toUserInfo()
        }
    }

    override suspend fun updateUserEmail(userId: String, email: String, currentUser: UserEntity): UserDto? {
        return dbTransaction(dispatcher) {
            userLds.updateUserEmail(userId.toUuid(), email, currentUser).checkResult(UserNotFoundException())
                .toUserDto()
        }
    }

    override suspend fun updateUserPassword(userId: String, password: String, currentUser: UserEntity): UserDto? {
        return dbTransaction(dispatcher) {
            userLds.updateUserPassword(userId.toUuid(), password, currentUser).checkResult(UserNotFoundException())
                .toUserDto()
        }
    }

    override suspend fun updateUserType(userId: String, userType: UserType, currentUser: UserEntity): UserDto? {
        return dbTransaction(dispatcher) {
            userLds.updateUserType(userId.toUuid(), userType, currentUser).checkResult(UserNotFoundException())
                .toUserDto()
        }
    }

    override suspend fun updateUserInfo(userId: String, user: UserInfoDto, currentUser: UserEntity): UserInfoDto? {
        return dbTransaction(dispatcher) {
            userLds.updateUserInfo(userId.toUuid(), user, currentUser).checkResult(UserNotFoundException()).toUserInfo()
        }
    }

    override suspend fun deleteUser(userId: String, currentUser: UserEntity) {
        dbTransaction(dispatcher) {
            userLds.deleteUser(userId.toUuid(), currentUser).checkResult(UserNotFoundException())
        }
    }

    override suspend fun deleteUserInfo(userId: String, currentUser: UserEntity) {
        dbTransaction(dispatcher) {
            userLds.deleteUserInfo(userId.toUuid(), currentUser).checkResult(UserNotFoundException())
        }
    }

    override suspend fun cleanUsersTable(): Int {
        return dbTransaction(dispatcher) { userLds.cleanUsersTable().checkResult(UserNotFoundException()) } ?: 0
    }

    override suspend fun cleanUserInfoTable(): Int {
        return dbTransaction(dispatcher) { userLds.cleanUserInfoTable().checkResult(UserNotFoundException()) } ?: 0
    }
}