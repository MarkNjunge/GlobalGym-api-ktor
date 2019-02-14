package com.marknjunge.router

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.*

fun Routing.router() {
    get("/"){
        call.respond("/GlobalGym API")
    }
    route("/api") {
        this@router.users()
        this@router.gyms()
        this@router.instructors()
        this@router.sessions()
    }
}