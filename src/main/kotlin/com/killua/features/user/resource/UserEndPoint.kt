@file:OptIn(KtorExperimentalLocationsAPI::class)

package com.killua.features.user.resource

import com.killua.features.user.domain.UserRepository
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserType
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

@kotlinx.serialization.Serializable
data class SignupRequest(val email: String, val password: String, val createdBy: String, val userType: String)

@kotlinx.serialization.Serializable
data class DeleteImageRequest(val userId: String, val imageId: String)


fun Application.userEndpoint() {
    routing {
        route("/user") {
            val userRepository by inject<UserRepository>()

            //create user

            post {
                val signupRequest = call.receive<SignupRequest>()

                val user = UserDto(
                    email = signupRequest.email,
                    password = signupRequest.password,
                    createdBy = signupRequest.createdBy,
                    userType = UserType.valueOf(signupRequest.userType)
                )

                val created = userRepository.createUser(user)

                call.respond(HttpStatusCode.Created, created.toString())

            }
            //get all users
            get {

                val user: Any? = when (val id = call.request.queryParameters["id"]) {
                    null -> {
                        userRepository.getAllUsers()
                    }
                    else -> {
                        userRepository.getUser(id)
                    }
                }
                val response = user ?: "No user found"
                call.respond(response)
            }


            delete {
                val userId = call.request.queryParameters["id"].toString()
                userId.ifBlank {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }
                val result = userRepository.removeUser(userId)
                if (result)
                    call.respond(HttpStatusCode.Accepted)
                else call.respond(HttpStatusCode.BadRequest)
            }


        }
    }
}
