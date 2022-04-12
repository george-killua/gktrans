package com.killua.features.user.data

import com.killua.extenstions.encoded
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserInfoEntity
import com.killua.features.user.data.dao.UserTable
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserInfoDto
import com.killua.features.user.domain.model.UserType
import java.util.*


class UserLocalDataSourceImpl : UserLocalDataSource {
    override suspend fun getAllUsers(companyId: UUID): List<UserEntity> {
        return UserEntity.find { UserTable.company eq companyId }.toList().ifEmpty { emptyList() }
    }

    override suspend fun getAllUsers(): List<UserEntity> {
        return UserEntity.all().toList()
    }

    override suspend fun getUser(userId: UUID): UserEntity? {
        return UserEntity.findById(userId)
    }

    override suspend fun getUserInfo(userId: UUID): UserInfoEntity? {
        return UserEntity.findById(userId)?.userInfo
    }

    override suspend fun getUserLoginCredential(email: String, password: String): UserEntity? {
        return UserEntity.find { UserTable.email eq email }.firstOrNull()
    }

    override fun getUserLoginCredentiale(email: String, password: String): UserEntity? {
        return UserEntity.find { UserTable.email eq email }.firstOrNull()
    }

    override suspend fun addUser(user: UserDto, currentUser: UserEntity): UserEntity {
        return UserEntity.new {
            this.email = user.email
            this.userType = user.userType
            this.password = user.password.encoded()
            this.company = currentUser.company
            create(currentUser)
        }
    }

    override  fun addUserTest(user: UserDto): UserEntity {
        return UserEntity.new {
            this.email = user.email
            this.userType = user.userType
            this.password = user.password.encoded()
            create(this)
        }
    }

    override suspend fun addUserInfo(userId: UUID, userInfo: UserInfoDto, currentUser: UserEntity): UserInfoEntity? {
        val tempUserInfo = UserInfoEntity.new {
            this.firstname = userInfo.firstname
            this.lastname = userInfo.lastname
            this.phoneNumber = userInfo.phoneNumber
            this.nationality = userInfo.nationality
            this.sex = userInfo.sex
            this.street = userInfo.street
            this.streetNr = userInfo.streetNr
            this.city = userInfo.city
            this.areaCode = userInfo.areaCode
            this.additionalInfo = userInfo.additionalInfo
            create(currentUser)
        }
        getUser(userId)?.userInfo = tempUserInfo
        return getUser(userId)?.userInfo
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
            this.password = password.encoded()
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

    override suspend fun updateUserInfo(userId: UUID, user: UserInfoDto, currentUser: UserEntity): UserInfoEntity? {

        val info = getUser(userId)?.userInfo
        info?.apply {
            firstname = user.firstname
            lastname = user.lastname
            phoneNumber = user.phoneNumber
            nationality = user.nationality
            sex = user.sex
            street = user.street
            streetNr = user.streetNr
            city = user.city
            areaCode = user.areaCode
            additionalInfo = user.additionalInfo
            update(currentUser)
        }
        return info
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