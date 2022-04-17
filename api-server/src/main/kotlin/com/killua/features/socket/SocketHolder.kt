package com.killua.features.socket

import io.ktor.websocket.*

data class SocketHolder(val sessionId: String, val socketSession: WebSocketSession)
