package com.marknjunge.router

import com.marknjunge.db.GymDao
import com.marknjunge.db.UserDao
import com.marknjunge.model.ApiResponse
import com.marknjunge.model.User
import com.marknjunge.utils.ItemNotFoundException
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.users(userDao: UserDao) {

    route("/users") {
        get("/") {
            call.respond(HttpStatusCode.OK, userDao.selectAll())
        }
        get("/{id}") {
            val id = call.parameters["id"]!!

            val user = userDao.selectById(id)
            if (user == null) {
                throw ItemNotFoundException("There is no user with id $id")
            } else {
                call.respond(HttpStatusCode.OK, user)
            }
        }
        post("/{id}/gym/add") {
            val gymId = call.receive<Map<String, String>>()["gymId"]
            val userId = call.parameters["id"]!!

            if ( gymId == null) {
                call.respond(HttpStatusCode.BadRequest, "gymId is required")
                return@post
            }

            userDao.setPreferredGym(userId, gymId)

            call.respond(HttpStatusCode.OK, ApiResponse("Gym added"))
        }
        post("/{id}/gym/remove") {
            val userId = call.parameters["id"]!!
            userDao.removePreferredGym(userId)
            call.respond(HttpStatusCode.OK, ApiResponse("Gym removed"))
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