package com.marknjunge

import com.marknjunge.model.ApiResponse
import com.marknjunge.router.router
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.gson.*

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
        exception<Throwable> { cause ->
            log.error(cause.message)
            call.respond(HttpStatusCode.InternalServerError)
        }
        status(HttpStatusCode.NotFound) {
            call.respond(HttpStatusCode.NotFound, ApiResponse("Cannot ${call.request.httpMethod.value} ${call.request.path()}"))
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
        router()
    }
}
