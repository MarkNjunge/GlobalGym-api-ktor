package com.marknjunge.router

import com.marknjunge.Config
import com.marknjunge.db.Database
import io.ktor.application.call
import io.ktor.http.content.resource
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.respond
import io.ktor.routing.*

fun Routing.staticRouter(){
    resource("/", "index.html", "web")
    static("/css") {
        resources("web/css")
    }
}

fun Routing.apiRouter() {

    val config = Config(application)

    val database = Database(config.database.url, config.database.username, config.database.password)

    route("/api") {
        get("/") {
            call.respond("GlobalGym API")
        }
        users(database.userDao)
        gyms(database.gymDao)
        instructors(database.instructorsDao)
        sessions(database.sessionsDao)
    }
}