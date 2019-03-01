package com.marknjunge

import com.marknjunge.model.ApiError
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
import io.ktor.gson.*
import io.ktor.util.pipeline.PipelinePhase
import org.jdbi.v3.core.statement.UnableToCreateStatementException
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
            if (cause.javaClass != UnableToCreateStatementException::class) {
                log.error("${cause.javaClass.simpleName}: ${cause.message}")
            }

            if (cause.message!!.contains("duplicate")) {
                val message = cause.message!!
                    .split("Detail: Key ")[1]
                    .split(" already exists.")[0]
                    .replace("(", "")
                    .replace(")", "")
                    .replace("=", " ")
                call.respond(HttpStatusCode.Conflict, ApiResponse("$message already exists"))
            } else {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ApiError("An error has occurred", cause.message.toString())
                )
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

    install(ContentNegotiation) {
        gson {}
    }

    val loggingPhase = PipelinePhase("CustomLogging")
    insertPhaseBefore(ApplicationCallPipeline.Monitoring, loggingPhase)

    intercept(loggingPhase) {
        proceed()
        log.trace(
            "${call.response.status()?.value ?: "Unhandled"}: ${call.request.httpMethod.value} - ${call.request.path()}"
        )
    }

    val vuepressDistFolder = File("resources/docs/.vuepress/dist")
    if (!vuepressDistFolder.exists()) log.warn("vuepress has not been built. /docs will not be available.")

    val config = Config(this)

    routing {
        application.intercept(ApplicationCallPipeline.Call) {
            if (call.request.path().contains("api")) {
                val authHeader = call.request.header("AuthKey")

                if (authHeader.isNullOrEmpty()){
                    call.respond(HttpStatusCode.Unauthorized, ApiResponse("Authentication not provided"))
                    finish()
                    return@intercept
                }

                if (authHeader != config.authKey) {
                    call.respond(HttpStatusCode.Forbidden, ApiResponse("Invalid authentication"))
                    finish()
                    return@intercept
                }
            }
        }

        staticRouter()
        apiRouter()
    }
}
