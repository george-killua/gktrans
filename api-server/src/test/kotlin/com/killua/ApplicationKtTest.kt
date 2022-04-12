package com.killua

import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ApplicationKtTest {

    @Test
    fun testGetSpecificDog() {
        val test = withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(
                    HttpStatusCode.OK,
                    response.status()
                )
            }
        }

    }

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }
}