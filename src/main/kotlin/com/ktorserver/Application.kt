package com.ktorserver

import com.ktorserver.plugins.*
import com.ktorserver.repository.UserRepository
import com.ktorserver.service.JwtService
import com.ktorserver.service.UserService
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

//fun main() {
//    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
//        .start(wait = true)
//}

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    val userRepository = UserRepository()
    val userService = UserService(userRepository)
    val jwtService = JwtService(this, userService)

    configureSerialization()
    configureSecurity(jwtService)
    configureRouting(jwtService, userService)
    configureRouting(jwtService, userService)
}
