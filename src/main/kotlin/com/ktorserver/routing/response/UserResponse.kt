package com.ktorserver.routing.response

import com.ktorserver.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserResponse(
    @Serializable (with = UUIDSerializer::class)
    val id: UUID,
    val username: String,
)