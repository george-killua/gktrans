package com.killua.features.user.data

import com.killua.extenstions.toUuid
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserInfoEntity
import com.killua.features.user.data.dao.UserTable
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserInfoDto
import com.killua.features.user.domain.model.UserType
import org.jetbrains.exposed.sql.and
import java.util.*


class UserLocalDataSourceImpl() : UserLocalDataSource {
    override suspend fun getAllUsers(companyId: UUID): List<UserEntity> {
        return UserEntity.find { UserTable.company eq companyId }.toList().ifEmpty { emptyList() }
    }

    override suspend fun getAllUsers(): List<UserEntity> {
        return UserEntity.all().toList()
    }

    override suspend fun getUser(uuid: UUID): UserEntity? {
        return UserEntity.findById(uuid)
    }

    override suspend fun getUserInfo(uuid: UUID): UserInfoEntity? {
        return UserEntity.findById(uuid)?.userInfo
    }

    override suspend fun getUserLoginCredential(email: String, password: String): UserEntity? {
        return UserEntity.find { UserTable.email eq email and (UserTable.password eq password) }.firstOrNull()
    }

    override suspend fun addUser(user: UserDto, currentUser: UserEntity): UserEntity {
        return UserEntity.new {
            this.email = user.email
            this.userType = user.userType
            this.password = user.password
            this.company = currentUser.company
            create(currentUser)
        }
    }

    override suspend fun addUserInfo(user: UserInfoDto, currentUser: UserEntity): UserInfoEntity {
        return UserInfoEntity.new {
            this.firstname = user.firstname
            this.lastname = user.lastname
            this.phoneNumber = user.phoneNumber
            this.nationality = user.nationality
            this.sex = user.sex
            this.street = user.street
            this.streetNr = user.streetNr
            this.city = user.city
            this.areaCode = user.areaCode
            this.additionalInfo = user.additionalInfo
            create(currentUser)
        }
    }

    override suspend fun updateUser(user: UserDto, currentUser: UserEntity): UserEntity? {
        return UserEntity.findById(user.id!!.toUuid())?.run {
            this.email = user.email
            this.userType = user.userType
            this.password = user.password
            update(currentUser)
            this
        }
    }

    override suspend fun updateUserEmail(userId: UUID, email: String, currentUser: UserEntity): UserEntity? {
        return UserEntity.findById(userId)?.run {
            this.email = email
            update(currentUser)
            this
        }
    }

    override suspend fun updateUserPassword(userId: UUID, password: String, currentUser: UserEntity): UserEntity? {
        return UserEntity.findById(userId)?.run {
            this.password = password
            update(currentUser)
            this
        }
    }

    override suspend fun updateUserType(userId: UUID, userType: UserType, currentUser: UserEntity): UserEntity? {
        return UserEntity.findById(userId)?.run {
            this.userType = userType
            update(currentUser)
            this
        }
    }

    override suspend fun updateUserInfo(user: UserInfoDto, currentUser: UserEntity): UserInfoEntity? {
        return UserInfoEntity.findById(user.id.toUuid())?.let {
            it.firstname = user.firstname
            it.lastname = user.lastname
            it.phoneNumber = user.phoneNumber
            it.nationality = user.nationality
            it.sex = user.sex
            it.street = user.street
            it.streetNr = user.streetNr
            it.city = user.city
            it.areaCode = user.areaCode
            it.additionalInfo = user.additionalInfo
            it.update(currentUser)
            it
        }

    }


    override suspend fun deleteUser(userId: UUID, currentUser: UserEntity) {
        UserEntity.findById(userId)?.softDelete(currentUser)
    }

    override suspend fun deleteUserInfo(userId: UUID, currentUser: UserEntity) {
        UserEntity.findById(userId)?.userInfo?.softDelete(currentUser)
    }

    override suspend fun cleanUsersTable(): Int {
        return UserEntity.all().sumOf { it.cleanSomeOfIt() }
    }

    override suspend fun cleanUserInfoTable(): Int {
        return UserInfoEntity.all().sumOf { it.cleanSomeOfIt() }
    }

}