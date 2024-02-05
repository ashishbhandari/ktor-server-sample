package com.ktorserver.routing

import com.ktorserver.model.User
import com.ktorserver.routing.request.UserRequest
import com.ktorserver.routing.response.UserResponse
import com.ktorserver.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userRoute(userService: UserService) {

    post {
        val userRequest = call.receive<UserRequest>()
        val createdUser = userService.save(
            user = userRequest.toModel()
        ) ?: return@post call.respond(HttpStatusCode.BadRequest)

        call.response.header(
            name = "id",
            value = createdUser.id.toString()
        )

        call.respond(
            message = HttpStatusCode.Created
        )
    }

    get {
        val users = userService.findAll()
        call.respond(
            message = users.map(User::toResponse)
        )
    }

    get("/{id}") {
        val id: String = call.parameters["id"]
            ?: return@get call.respond(HttpStatusCode.BadRequest)

        val foundUser = userService.findById(id)
            ?: return@get call.respond(HttpStatusCode.NotFound)
        call.respond(
            message = foundUser.toResponse()
        )
    }

}

/**
 * Extension function
 */
private fun UserRequest.toModel(): User =
    User(
        id = UUID.randomUUID(),
        username = this.username,
        password = this.password
    )

private fun User.toResponse(): UserResponse =
    UserResponse(
        id = this.id,
        username = this.username,
    )