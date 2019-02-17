package com.marknjunge.router

import com.marknjunge.db.GymDao
import com.marknjunge.db.UserDao
import com.marknjunge.model.ApiResponse
import com.marknjunge.model.User
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.users(userDao: UserDao, gymDao: GymDao) {
    userDao.createTable()

    route("/users") {
        get("/") {
            call.respond(HttpStatusCode.OK, userDao.selectAll())
        }
        get("/{id}") {
            val id = call.parameters["id"]
            if (id.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, ApiResponse("id path parameter is required"))
                return@get
            }

            val user = userDao.selectById(id)
            if (user == null) {
                call.respond(HttpStatusCode.ExpectationFailed, ApiResponse("There is no user with id $id"))
            } else {
                call.respond(HttpStatusCode.OK, user)
            }
        }
        post("/create") {
            val user = call.receive<User>()
            userDao.insert(user)
            call.respond(HttpStatusCode.Created, user)
        }
        post("/update") {
            val user = call.receive<User>()
            userDao.update(user)
            call.respond(HttpStatusCode.OK, user)
        }
    }
}