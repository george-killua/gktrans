package com.killua.plugins

import com.killua.extenstions.encoded
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.domain.UsersRepo
import com.killua.inject
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val AUTHENTICATION_HEADER = "parameterization"
fun Application.configureSecurity() {
    val usersRepo by inject<UsersRepo>()
    authentication {
        basic(name = AUTHENTICATION_HEADER) {
            realm = "Ktor Server"
            validate { credentials ->
                usersRepo.getUserLoginCredential(credentials.name, credentials.password.encoded()!!)
            }
        }


    }

    routing {
        authenticate(AUTHENTICATION_HEADER) {
            get("/protected/route/basic") {
                val principal = call.principal<UserEntity>()!!
                call.respondText("Hello ${principal.email}")
            }
        }

    }
}
