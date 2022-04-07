package com.killua.plugins

import com.killua.features.socket.UserSession
import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.sessions.*
import io.ktor.util.*
import io.ktor.websocket.*
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

    intercept(ApplicationCallPipeline.Features) {
        if (call.sessions.get<UserSession>() == null) {
            val id = generateNonce()
            call.sessions.set(UserSession(id))

        }
    }

}
