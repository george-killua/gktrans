package com.killua.features.socket

import com.killua.extenstions.getSessionId
import com.killua.features.user.domain.model.UserDto
import io.ktor.application.ApplicationCall
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import com.killua.logger.ApiLogger



    fun ApplicationCall.notifySocketOfChange( user: UserDto) {
         val apiLogger: ApiLogger by inject()
         val socketServer: SocketServer by inject()
        application.launch {
            val sessionId = getSessionId()
            apiLogger.log("Notify socket", "sessionId $sessionId")
            socketServer.sendUpdateToUser(user, sessionId)
        }
    }
