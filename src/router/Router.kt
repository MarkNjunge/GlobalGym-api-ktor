package com.marknjunge.router

import com.marknjunge.Config
import com.marknjunge.db.Database
import io.ktor.application.call
import io.ktor.http.content.*
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.response.respondFile
import io.ktor.routing.*
import java.io.File

fun Routing.staticRouter() {
    resource("/", "index.html", "web")
    static("/css") {
        resources("web/css")
    }
    // Serving static assets using resources() doesn't work with .folder
    route("/assets") {
        get("/{...}") {
            val pathName = call.request.path().split("assets/")[1]
            call.respondFile(File("resources/docs/.vuepress/dist/assets/$pathName"))
        }
    }
    // Respond to requests to /docs with vuepress generated files
    route("/docs") {
        get("/{...}") {
            val split = call.request.path().split("docs/")
            if (split.size == 1) {
                call.respondFile(File("resources/docs/.vuepress/dist/docs/index.html"))
                return@get
            }

            val f = File("resources/docs/.vuepress/dist/docs/${split[1]}")
            if (f.exists()) {
                call.respondFile(f)
            } else {
                call.respondFile(File("resources/docs/.vuepress/dist/404.html"))
            }
        }
    }
}

fun Routing.apiRouter() {

    val config = Config(application)

    val database = Database(config.database.url, config.database.username, config.database.password)

    route("/api") {
        get("/") {
            call.respond("GlobalGym API")
        }
        users(database.userDao, database.gymDao)
        gyms(database.gymDao)
        instructors(database.instructorsDao, database.gymDao)
        sessions(database.sessionsDao)
    }
}