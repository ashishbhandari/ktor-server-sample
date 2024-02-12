package com.ktorserver.routing

import com.ktorserver.model.User
import com.ktorserver.routing.request.UserRequest
import com.ktorserver.routing.response.UserResponse
import com.ktorserver.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userRoute(userService: UserService) {

    //specify a handler for POST requests.
    post {
        val userRequest = call.receive<UserRequest>()
        //If the save returns a null value, we return 400 Bad Request with the call.respond() function.
        //if the user was saved successfully, we respond with a 201 Created status and pass its identifier as the id header value with call.response.header() function.
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


//another handler responsible for GET /api/user requests:
    authenticate {
        get {
            val users = userService.findAll()
            call.respond(
                message = users.map(User::toResponse)
            )
        }
    }
    authenticate("another-auth") {
        get("/{id}") {
            val id: String = call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            val foundUser = userService.findById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound)
            if (foundUser.username != extractPrincipalUsername(call))
                return@get call.respond(HttpStatusCode.NotFound)

            call.respond(
                message = foundUser.toResponse()
            )
        }
    }

}

private fun extractPrincipalUsername(call: ApplicationCall): String? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("username")
        ?.asString()

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