package com.ktorserver.repository

import com.ktorserver.model.User
import java.util.UUID


/*
    Not to lose focus from the clue of this article- authentication in Ktor and
    JWT- we introduce a dummy repository, which uses the mutable list to store users in memory.
 */
class UserRepository {

    private val users = mutableListOf<User>()

    fun findById(id: UUID): User? {
        return users.firstOrNull { it.id == id }
    }

    fun findAll(): List<User> {
        return users
    }

    fun findByUserName(username: String): User? {
        return users.firstOrNull {
            it.username == username
        }
    }

    fun save(user: User) : Boolean{
        return users.add(user)
    }
}