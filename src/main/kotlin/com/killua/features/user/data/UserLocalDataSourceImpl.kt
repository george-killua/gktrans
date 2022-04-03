package com.killua.features.user.data


import com.killua.extenstions.DatabaseExt.dbTransaction
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserTable
import com.killua.features.user.domain.mapper.toUser
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserUpdateDetails
import org.jetbrains.exposed.sql.and
import java.util.*

class UserLocalDataSourceImpl() : UserLocalDataSource {
    override fun getAllUsers(): List<UserEntity> {
        val query = UserEntity.all()
        return query.map { it }
    }

    override suspend fun getUser(uuid: String): UserEntity? =
        UserEntity.findById(UUID.fromString(uuid))


    override suspend fun getUser(email: String, password: String): UserEntity? {
        return dbTransaction {
            UserEntity.find {
                UserTable.email eq email and UserTable.password.eq(password)
            }.firstOrNull()
        }
    }

    override fun addUser(user: UserDto): UserEntity = UserEntity.new {
        email = user.email
        password = user.password
       // createdBy = user.createdBy
        userType = user.userType

    }


    //val principal = call.principal<JWTPrincipal>()

    override fun removeImage(userId: String): Boolean {
        val updateDetails = UserEntity.findById(UUID.fromString(userId))
        updateDetails?.pic = null
        updateDetails?.storeWrittenValues()
        return UserEntity.findById(UUID.fromString(userId))?.toUser()?.pic == null
    }

    override suspend fun removeUser(userId: String): Boolean {
        UserEntity.findById(UUID.fromString(userId))?.delete()
        return getUser(userId) == null
    }
}