package com.marknjunge.router

import com.marknjunge.Config
import com.marknjunge.db.Database
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.*

fun Routing.router() {

    val config = Config(application)

    val database = Database(config.database.url, config.database.username, config.database.password)

    get("/"){
        call.respond("GlobalGym API")
    }
    route("/api") {
        get("/"){
            call.respond("GlobalGym API")
        }
        users()
        gyms(database.gymDao)
        instructors()
        sessions()
    }
}