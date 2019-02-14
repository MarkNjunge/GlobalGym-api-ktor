package com.marknjunge.router

import com.marknjunge.model.ApiResponse
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Routing.instructors(){
    route("/instructors") {
        get("/") {
            call.respond(HttpStatusCode.NotImplemented, ApiResponse("Not implemented"))
        }
        get("/{id}") {
            call.respond(
                HttpStatusCode.NotImplemented,
                ApiResponse(call.parameters["id"] ?: "Failed to parse path")
            )
        }
        post("/create") {
            call.respond(HttpStatusCode.NotImplemented, ApiResponse("Not implemented"))
        }
        post("/update") {
            call.respond(HttpStatusCode.NotImplemented, ApiResponse("Not implemented"))
        }
    }
}