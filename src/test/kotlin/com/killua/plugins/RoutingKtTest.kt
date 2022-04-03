package com.killua.plugins;

import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.exposedLogger
import kotlin.test.Test

public class RoutingKtTest {

    @OptIn(KtorExperimentalLocationsAPI::class)
    @Test
    fun testGetEmptyRoute() {
        withTestApplication({ configureRouting() }) {

            handleRequest(HttpMethod.Get, "").apply {
                print(this.response)
            }
        }
    }
}