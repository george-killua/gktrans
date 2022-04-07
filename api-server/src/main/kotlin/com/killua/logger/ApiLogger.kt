package com.killua.logger

import org.slf4j.LoggerFactory

class ApiLogger {
    private val logger = LoggerFactory.getLogger("ApiLogger")

    fun log(tag: String, text: String) {
        logger.info("$tag: $text")
    }
}
/*
val socketModule = DI.Module("socketModule") {
    bind() from single { SocketServer(instance(), instance()) }
    from single{ SocketNotifier(instance(), instance()) }
}
*/
