package com.ktorserver.plugins

import com.ktorserver.routing.userRoute
import com.ktorserver.service.UserService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(userService: UserService) {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

    routing {
        route("/api/user") {
            userRoute(userService)
        }
    }
}
