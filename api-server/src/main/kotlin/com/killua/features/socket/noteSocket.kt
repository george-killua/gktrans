package com.killua.features.socket

import com.killua.extenstions.getSessionId
import com.killua.extenstions.user
import com.killua.features.user.domain.mapper.toUserDto
import com.killua.features.user.domain.model.UserDto
import com.killua.inject
import com.killua.logger.ApiLogger
import io.ktor.websocket.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.channels.consumeEach
import org.koin.ktor.ext.inject


fun Route.notesSocket() {
    val apiLogger: ApiLogger by inject()
    val socketServer: SocketServer by inject()

    webSocket("/notify/ws") {

        apiLogger.log("Socket userSocketSession", call.user.toString())
        val sessionId = call.getSessionId()
        if (sessionId == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
            return@webSocket
        }
        apiLogger.log("Socket sessionId", sessionId)

        try {
            incoming.consumeEach { frame ->
                apiLogger.log("Socket incoming frame", frame.toString())

                getUser()?.let { user ->
                    apiLogger.log("Socket incoming user", user.toString())
                    socketServer.addListener(sessionId, user, this)
                }
            }
        } finally {
            getUser()?.let { user ->
                apiLogger.log("Socket ended", user.toString())
                socketServer.removeListener(sessionId, user, this)
            }
        }
    }
}

private suspend fun WebSocketServerSession.getUser(): UserDto? {
    val userId = call.user?.id?.value
    return if (userId == null) {
        close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "User id is null"))
        null
    } else {
        call.user?.toUserDto()
    }
}


