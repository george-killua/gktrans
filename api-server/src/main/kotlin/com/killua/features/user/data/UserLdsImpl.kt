package com.killua.features.user.data

import com.killua.extenstions.*
import com.killua.features.company.data.CompaniesLDS
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserInfoEntity
import com.killua.features.user.data.dao.UserInfosTable
import com.killua.features.user.data.dao.UserTable
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserInfoDto
import com.killua.features.user.domain.model.UserType
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.or
import java.util.*


class UserLdsImpl(private val companyLds: CompaniesLDS) : UserLds {
    override suspend fun getAllUsers(companyId: UUID): DbResult<List<UserEntity>> {
        return companyLds.getUsers(companyId).checkNullability()
    }

    override suspend fun getAllUsers(): DbResult<List<UserEntity>> {
        val users = UserEntity.all().toList()
        return if (users.isNotEmpty())
            DbResult.FoundThings(users)
        else
            DbResult.NothingsFound
    }

    override suspend fun searchUser(keyword: String): DbResult<List<UserEntity>> {
        val ids =
            UserInfoEntity.find { UserInfosTable.firstname like "%$keyword%" or (UserInfosTable.lastname like "%$keyword") }
                .map { it.id }
        return UserEntity.find { UserTable.email like "%$keyword%" or (UserTable.userInfo inList ids) }.toList()
            .checkNullability()

    }

    override suspend fun getUser(userId: UUID): DbResult<UserEntity> {
        return UserEntity.findById(userId).checkNullability()

    }

    override suspend fun getUserInfo(userId: UUID): DbResult<UserInfoEntity> {
        return UserEntity.findById(userId)?.userInfo.checkNullability()
    }

    override suspend fun getUserLoginCredential(email: String, password: String): DbResult<UserEntity> {
        val encodidPassword: String = password.encoded()!!
        return UserEntity.find { UserTable.email eq email and (UserTable.password eq encodidPassword) }
            .firstOrNull()
            .checkNullability()
    }


    override suspend fun addUser(user: UserDto, currentUser: UserEntity): DbResult<UserEntity> {
        return UserEntity.new {
            this.email = user.email
            this.userType = user.userType
            this.password = user.password.encoded()!!
            this.company = currentUser.company
            create(currentUser)
        }.checkNullability()
    }

    override fun addUserTest(user: UserDto): DbResult<UserEntity> {
        return UserEntity.new {
            this.email = user.email
            this.userType = user.userType
            this.password = user.password.encoded()!!
            create(this)
        }.checkNullability()
    }

    override suspend fun addUserInfo(
        userId: UUID,
        userInfo: UserInfoDto,
        currentUser: UserEntity
    ): DbResult<UserInfoEntity> {
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
        val user = getUser(userId).checkResult(UserNotFoundException())
        user.userInfo = tempUserInfo
        return user.userInfo.checkNullability()
    }

    override suspend fun updateUserEmail(userId: UUID, email: String, currentUser: UserEntity): DbResult<UserEntity> {
        return UserEntity.findById(userId)?.run {
            this.email = email
            update(currentUser)
            this
        }.checkNullability()
    }

    override suspend fun updateUserPassword(
        userId: UUID,
        password: String,
        currentUser: UserEntity
    ): DbResult<UserEntity> {
        return UserEntity.findById(userId)?.run {
            this.password = password.encoded()!!
            update(currentUser)
            this
        }.checkNullability()
    }

    override suspend fun updateUserType(
        userId: UUID,
        userType: UserType,
        currentUser: UserEntity
    ): DbResult<UserEntity> {
        return UserEntity.findById(userId)?.run {
            this.userType = userType
            update(currentUser)
            this
        }.checkNullability()
    }

    override suspend fun updateUserInfo(
        userId: UUID,
        user: UserInfoDto,
        currentUser: UserEntity
    ): DbResult<UserInfoEntity> {
        val updatedUser = getUser(userId).checkResult(UserNotFoundException())
        val info = updatedUser.userInfo?.run {
            user.firstname?.let { firstname = it }
            println(firstname)
            user.lastname?.let { lastname = it }
            user.phoneNumber?.let { phoneNumber = it }
            user.nationality?.let { nationality = it }
            user.sex?.let { sex = it }
            user.street?.let { street = it }
            user.streetNr?.let { streetNr = it }
            user.city?.let { city = it }
            user.areaCode?.let { areaCode = it }
            user.additionalInfo?.let { additionalInfo = it }
            update(currentUser)
            return@run this
        }.checkNullability()
        return info
    }


    override suspend fun deleteUser(userId: UUID, currentUser: UserEntity): DbResult<Unit> {
        return UserEntity.findById(userId)?.softDelete(currentUser).checkNullability()
    }

    override suspend fun deleteUserInfo(userId: UUID, currentUser: UserEntity): DbResult<Unit> {
        return UserEntity.findById(userId)?.userInfo?.softDelete(currentUser).checkNullability()
    }

    override suspend fun cleanUsersTable(): DbResult<Int> {
        return UserEntity.all().sumOf { it.cleanSomeOfIt() }.checkNullability()
    }

    override suspend fun cleanUserInfoTable(): DbResult<Int> {
        return UserInfoEntity.all().sumOf { it.cleanSomeOfIt() }.checkNullability()
    }

}

