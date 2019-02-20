package com.marknjunge.router

import com.marknjunge.db.SessionsDao
import com.marknjunge.miniUUID
import com.marknjunge.model.ApiResponse
import com.marknjunge.model.Session
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.sessions(sessionsDao: SessionsDao) {
    route("/sessions") {
        get("/{id}") {
            val id = call.parameters["id"]
            if (id.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, ApiResponse("id path parameter is required"))
                return@get
            }

            call.respond(HttpStatusCode.OK, sessionsDao.selectForUser(id))
        }
        post("/create") {
            val session = call.receive<Session>().copy(id = miniUUID())
            sessionsDao.insert(session)
            call.respond(HttpStatusCode.Created, ApiResponse("Session created"))
        }
        post("/update") {
            val session = call.receive<Session>()
            sessionsDao.update(session)
            call.respond(HttpStatusCode.OK, ApiResponse("Session update"))
        }
        delete("/delete/{id}") {
            val id = call.parameters["id"]
            if (id.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, ApiResponse("id path parameter is required"))
                return@delete
            }

            sessionsDao.deleteSession(id)
            call.respond(HttpStatusCode.OK, ApiResponse("Session deleted"))
        }
    }
}