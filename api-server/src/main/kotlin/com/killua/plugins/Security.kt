package com.killua.plugins

import com.killua.extenstions.encoded
import com.killua.features.user.domain.UsersRepo
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

const val AUTHENTICATION_HEADER = "parameterization"
fun Application.configureSecurity() {
    val usersRepo by inject<UsersRepo>()
    authentication {
        basic(name = AUTHENTICATION_HEADER) {
            realm = "Ktor Server"
            validate { credentials ->
                usersRepo.getUserLoginCredential(credentials.name, credentials.password.encoded())
            }
        }


    }

    routing {
        authenticate(AUTHENTICATION_HEADER) {
            get("/protected/route/basic") {
                val principal = call.principal<UserIdPrincipal>()!!
                call.respondText("Hello ${principal.name}")
            }
        }

    }
}
