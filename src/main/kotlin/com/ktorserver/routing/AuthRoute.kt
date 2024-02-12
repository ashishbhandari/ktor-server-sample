package com.ktorserver.routing

import com.ktorserver.routing.request.LoginRequest
import com.ktorserver.service.JwtService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoute(jwtService: JwtService) {

    post {
        val user = call.receive<LoginRequest>()
        val token: String? = jwtService.createJwtToken(user)
        token?.let {
            call.respond(hashMapOf("token" to token))
        } ?: call.respond(
            message = HttpStatusCode.Unauthorized
        )
    }

}