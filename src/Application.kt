package com.marknjunge

import com.marknjunge.model.ApiResponse
import com.marknjunge.router.apiRouter
import com.marknjunge.router.staticRouter
import com.marknjunge.utils.ItemNotFoundException
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.gson.*
import io.ktor.http.content.*
import java.io.File

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }
    install(StatusPages) {
        exception<ItemNotFoundException> {
            call.respond(HttpStatusCode.NotFound, ApiResponse(it.message!!))
        }
        exception<Throwable> { cause ->
            log.error("${cause.javaClass.name}: ${cause.message}")

            if (cause.message!!.contains("duplicate")) {
                val message = cause.message!!
                    .split("Detail: Key ")[1]
                    .split(" already exists.")[0]
                    .replace("(", "")
                    .replace(")", "")
                    .replace("=", " ")
                call.respond(HttpStatusCode.Conflict, ApiResponse("$message already exists"))
            } else {
                call.respond(HttpStatusCode.InternalServerError, ApiResponse("An error has occurred: ${cause.message}"))
            }
        }
        status(HttpStatusCode.NotFound) {
            // For api requests send json, for others send a 404 page
            if (call.request.path().contains("api")) {
                call.respond(
                    HttpStatusCode.NotFound,
                    ApiResponse("Cannot ${call.request.httpMethod.value} ${call.request.path()}")
                )
            } else {
                call.respondFile(File("resources/web/404.html"))
            }
        }
    }

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(ContentNegotiation) {
        gson {}
    }

    routing {
        staticRouter()
        apiRouter()
    }
}
