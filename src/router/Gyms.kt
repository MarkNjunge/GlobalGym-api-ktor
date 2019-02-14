package com.marknjunge.router

import com.marknjunge.model.ApiResponse
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Routing.gyms(){
    route("/gyms") {
        get("/") {
            call.respond(HttpStatusCode.NotImplemented, ApiResponse("Not implemented"))
        }
        get("/{id}") {
            call.respond(
                HttpStatusCode.NotImplemented,
                ApiResponse(call.parameters["id"] ?: "Failed to parse path")
            )
        }
        get("/nearby") {
            val lat = call.request.queryParameters["lat"]
            val lng = call.request.queryParameters["lng"]
            val radius = call.request.queryParameters["radius"]
            call.respond(HttpStatusCode.NotImplemented, ApiResponse("Not implemented. $lat, $lng, $radius"))
        }
        get("/search") {
            val name = call.request.queryParameters["name"]
            call.respond(HttpStatusCode.NotImplemented, ApiResponse("Not implemented. $name"))
        }
        post("/create") {
            call.respond(HttpStatusCode.NotImplemented, ApiResponse("Not implemented"))
        }
        post("/update") {
            call.respond(HttpStatusCode.NotImplemented, ApiResponse("Not implemented"))
        }
        route("/images") {
            post("/add") {
                call.respond(HttpStatusCode.NotImplemented, ApiResponse("Not implemented"))
            }
            post("/remove") {
                call.respond(HttpStatusCode.NotImplemented, ApiResponse("Not implemented"))
            }
        }

        route("/instructors") {
            post("/add") {
                call.respond(HttpStatusCode.NotImplemented, ApiResponse("Not implemented"))
            }
            post("/remove") {
                call.respond(HttpStatusCode.NotImplemented, ApiResponse("Not implemented"))
            }
        }
    }
}