@file:OptIn(KtorExperimentalLocationsAPI::class)

package com.killua.plugins

import com.killua.features.image.resource.imageEndpoint
import com.killua.features.user.resource.userEndpoint
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.get

@OptIn(KtorExperimentalLocationsAPI::class)
fun Application.configureRouting() {




    routing {
        route("/api") {

            route("/v1") {
                imageEndpoint()
                userEndpoint()
            }
        }
        get("/") {
            call.respondText("Hello World!")
        }
        get<MyLocation> {
            call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
        }
        // Register nested routes
        get<Type.Edit> {
            call.respondText("Inside $it")
        }
        get<Type.List> {
            call.respondText("Inside $it")
        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}

@Location("/location/{name}")
class MyLocation(val name: String, val arg1: Int = 42, val arg2: String = "default")

@Location("/type/{name}")
data class Type(val name: String) {
    @Location("/edit")
    data class Edit(val type: Type)

    @Location("/list/{page}")
    data class List(val type: Type, val page: Int)
}
