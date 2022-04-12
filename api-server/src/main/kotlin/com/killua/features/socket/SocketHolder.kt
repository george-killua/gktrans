package com.killua.features.socket

import io.ktor.http.cio.websocket.*

data class SocketHolder(val sessionId: String, val socketSession: WebSocketSession)
