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
        get("/css/*") {
            val pathName = call.request.path().split("assets/css/")[1]
            call.respondFile(File("resources/docs/.vuepress/dist/assets/css/$pathName"))
        }
        get("/img/*") {
            val pathName = call.request.path().split("assets/img/")[1]
            call.respondFile(File("resources/docs/.vuepress/dist/assets/img/$pathName"))
        }
        get("/js/*") {
            val pathName = call.request.path().split("assets/js/")[1]
            call.respondFile(File("resources/docs/.vuepress/dist/assets/js/$pathName"))
        }
    }
    // Respond to requests to /docs with vuepress generated files
    route("/docs") {
        get("/") {
            call.respondFile(File("resources/docs/.vuepress/dist/docs/index.html"))
        }
        // Handle requests to specific files. Note: This won't work with subfolders/subpaths
        get("*") {
            val pathName = call.request.path().split("docs/")[1]
            val f = File("resources/docs/.vuepress/dist/docs/$pathName")
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