package com.example.plugins

import com.example.room.sessions.ChatSession
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.*

fun Application.configureSecurity() {
    data class MySession(val count: Int = 0)
    install(Sessions) {
        cookie<ChatSession>("SESSION")
    }

    intercept(ApplicationCallPipeline.Features){
        if (call.sessions.get<ChatSession>() == null){
            val userName = call.parameters["username"] ?: "Guest"
            call.sessions.set(ChatSession(userName, generateNonce()))
        }
    }

}
