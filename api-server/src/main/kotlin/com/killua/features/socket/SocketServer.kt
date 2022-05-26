package com.killua.features.socket

import com.killua.features.user.domain.model.UserDto
import com.killua.logger.ApiLogger
import io.ktor.server.websocket.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

class SocketServer(
    private val apiLogger: ApiLogger,

    ) {

    private val listeners = ConcurrentHashMap<UserDto, MutableList<SocketHolder>>()

    fun addListener(sessionId: String, user: UserDto, webSocketSession: WebSocketSession) {
        val sockets = listeners.computeIfAbsent(user) { CopyOnWriteArrayList() }
        val holder = SocketHolder(sessionId, webSocketSession)

        if (sockets.contains(holder).not()) {
            sockets.add(holder)
            apiLogger.log("Socket Server", "add $holder for $user")
        }
    }

    fun removeListener(sessionId: String, user: UserDto, webSocketSession: WebSocketSession) {
        val connections = listeners[user]
        val holder = SocketHolder(sessionId, webSocketSession)
        apiLogger.log("Socket Server", "remove $holder from $user")
        connections?.remove(holder)
    }

    suspend fun sendUpdateToUser(user: UserDto, sessionId: String?) {
        apiLogger.log("Socket Server", "listeners $listeners")
        // val notes = notesStorage.getNotes(user)
        listeners[user]?.forEach { holder ->
            if (holder.sessionId != sessionId) {
                //apiLogger.log("Socket Server", "user $user, sessionId ${holder.sessionId}, notes $notes")
                try {
                    // val json = Json.encodeToString(ListSerializer(NoteSchema.serializer()), notes)
                    //    holder.socketSession.send(Frame.Text(json))
                } catch (e: Throwable) {
                    apiLogger.log("Socket Server", "sendUpdateToUserDto error $e")
                }
            }
        }
    }
}
