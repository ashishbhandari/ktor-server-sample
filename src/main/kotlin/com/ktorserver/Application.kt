package com.ktorserver

import com.ktorserver.plugins.*
import com.ktorserver.repository.UserRepository
import com.ktorserver.service.UserService
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

//fun main(args : Array<String>){
//    EngineMain.main(args)
//}

fun Application.module() {
    val userRepository = UserRepository()
    val userService = UserService(userRepository)

    configureSerialization()
    configureSecurity()
    configureRouting(userService)
}
