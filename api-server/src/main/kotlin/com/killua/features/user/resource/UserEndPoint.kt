@file:OptIn(KtorExperimentalLocationsAPI::class)

package com.killua.features.user.resource

import com.killua.features.user.domain.UserRepository
import com.killua.features.user.domain.model.UserInfoDto
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

//
@Location("/user/")
class UserIndex(val userid: String?=null) {
    //
    @Location("/all-company-id")
    data class GetAllUsers(val companyId: String?)

    //
    @Location("")
    data class GetUser(val userIndex: UserIndex)

    //
    @Location("/info")
    data class GetUserInfo(val userIndex: UserIndex)

    //didn't need it right now
    @Location("/login")
    data class GetUserLoginCredential(val email: String, val password: String)

    //
    @Location("")
    data class AddUser(val email: String, val password: String, val type: String)

    @Location("/info")
    data class AddUserInfo(
        val userIndex: UserIndex,
        val userInfoDto: UserInfoDto,
    )

    @Location("/email")
    data class UpdateUserEmail(val userIndex: UserIndex, val email: String)

    @Location("/password")
    data class UpdateUserPassword(val userIndex: UserIndex, val password: String)

    @Location("/type")
    data class UpdateUserType(val userIndex: UserIndex, val type: String)

    @Location("/info")
    data class UpdateUserInfo(
        val userIndex: UserIndex, val userInfoDto: UserInfoDto,
    )

    @Location("")
    class DeleteUser(val userIndex: UserIndex)

    @Location("/info")
    data class DeleteUserInfo(val userIndex: UserIndex)

    @Location("/clean-user-table")
    class CleanUsersTable()

    @Location("/clean-info-table")
    class CleanUserInfoTable()
}

fun Route.userEndpoint() {


    val userRepository by inject<UserRepository>()


    get<UserIndex.GetAllUsers> {
        call.respond("get all user company Id ${it.companyId}")
    }
    get<UserIndex.GetUser> {
        call.respond("GetUser user is ${it.userIndex.userid}")
    }
    post<UserIndex.GetUserInfo> {user->
        call.respond("did it user is ${user.userIndex.userid}")
    }
    post<UserIndex.AddUser> {
        call.respond("add user it ${it.email} ${it.type} ${it.password}")
    }
    post<UserIndex.AddUserInfo> {
        call.respond("add user it ${it.userIndex.userid} ${it.userInfoDto} ")
    }
    post<UserIndex.UpdateUserEmail> {
        call.respond("update email it ${it.userIndex.userid} ${it.email}")
    }
    post<UserIndex.UpdateUserType> {
        call.respond("update email it ${it.userIndex.userid} ${it.type}")
    }
    post<UserIndex.UpdateUserPassword> {
        call.respond("update email it ${it.userIndex.userid} ${it.password}")
    }

    delete<UserIndex.DeleteUser> { it ->
        call.respond("image deleted ${it.userIndex.userid}")
    }

    delete<UserIndex.DeleteUserInfo> { it ->

        call.respond(" ${it.userIndex.userid}")
    }
    delete<UserIndex.CleanUsersTable> {
        call.respond("cleanUsersTable")
    }
    delete<UserIndex.CleanUserInfoTable> {

        call.respond("CleanUserInfoTable")
    }

}