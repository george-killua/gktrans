package com.killua.plugins

import com.killua.features.socket.UserSession
import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.util.*
import java.time.Duration

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofMinutes(1)
    }
    install(Sessions) {
        val secretEncryptKey = hex("00112233445566778899aabbccddeeff")
        val secretAuthKey = hex("02030405060708090a0b0c")
        cookie<UserSession>("SESSION") {
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretAuthKey))

        }
    }

    intercept(Plugins) {
        if (call.sessions.get<UserSession>() == null) {
            val id = generateNonce()
            call.sessions.set(UserSession(id))

        }
    }

}
