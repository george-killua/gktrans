package com.killua.plugins

import com.killua.extenstions.encoded
import com.killua.features.user.domain.UserRepository
import io.ktor.auth.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
const val AUTHENTICATION_HEADER="parameterization"
fun Application.configureSecurity() {
val userRepository by inject<UserRepository>()
    authentication {
        basic(name = AUTHENTICATION_HEADER) {
            realm = "Ktor Server"
            validate { credentials ->
              userRepository.getUserLoginCredential(credentials.name , credentials.password.encoded())
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
