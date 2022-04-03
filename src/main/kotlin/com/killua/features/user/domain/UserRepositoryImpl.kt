package com.killua.features.user.domain

import com.killua.extenstions.DatabaseExt.dbTransaction
import com.killua.features.user.data.UserLocalDataSource
import com.killua.features.user.domain.mapper.toUser
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserUpdateDetails
import kotlinx.coroutines.runBlocking

class UserRepositoryImpl(private val userLds: UserLocalDataSource) : UserRepository {
    override suspend fun getAllUsers(): List<UserDto> {
        return dbTransaction {
            userLds.getAllUsers().map{ it.toUser() }
        }
    }

    override suspend fun createUser(user: UserDto): UserDto {
        return dbTransaction {
            userLds.addUser(user).toUser()
        }
    }

    override suspend fun getUser(uuid: String): UserDto? {
        return dbTransaction {
            runBlocking {
                userLds.getUser(uuid)?.toUser()
            }
        }
    }

    override suspend fun getUser(email: String, password: String): UserDto? {
        return dbTransaction {
            runBlocking {
                userLds.getUser(email, password)?.toUser()
            }
        }    }



    override suspend fun removeImage(userId: String): Boolean? = dbTransaction {
        userLds.removeImage(userId)
    }

    override suspend fun removeUser(userId: String) = dbTransaction {
        runBlocking {
            userLds.removeUser(userId)
        }
    }
}