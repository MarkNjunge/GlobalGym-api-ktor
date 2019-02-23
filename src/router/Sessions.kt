package com.marknjunge.router

import com.marknjunge.db.SessionsDao
import com.marknjunge.utils.miniUUID
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
            val id = call.parameters["id"]!!
            call.respond(HttpStatusCode.OK, sessionsDao.selectForUser(id))
        }
        post("/create") {
            val session = call.receive<Session>().copy(id = miniUUID())
            sessionsDao.insert(session)
            call.respond(HttpStatusCode.Created, session)
        }
        post("/update") {
            val session = call.receive<Session>()
            sessionsDao.update(session)
            call.respond(HttpStatusCode.OK, ApiResponse("Session updated"))
        }
        delete("/delete/{id}") {
            val id = call.parameters["id"]!!
            sessionsDao.deleteSession(id)
            call.respond(HttpStatusCode.OK, ApiResponse("Session deleted"))
        }
    }
}