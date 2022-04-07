package com.killua


import io.ktor.server.testing.*
import com.killua.plugins.*
import io.ktor.http.*
import io.ktor.locations.*
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ApplicationTest {
    @OptIn(KtorExperimentalLocationsAPI::class)
    @Test
    fun testRoot() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World!", response.content)
            }
        }
    }
}