package com.marknjunge.router

import com.marknjunge.db.GymDao
import com.marknjunge.utils.miniUUID
import com.marknjunge.model.ApiResponse
import com.marknjunge.model.Gym
import com.marknjunge.utils.ItemNotFoundException
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.*

fun Route.gyms(gymDao: GymDao) {

    route("/gyms") {
        get("/") {
            val gyms = gymDao.selectAll()
            call.respond(HttpStatusCode.OK, gyms)
        }
        get("/{id}") {
            val id = call.parameters["id"]!!

            val gym = gymDao.selectById(id)
            if (gym == null) {
                throw ItemNotFoundException("There is no gym with id $id")
            } else {
                call.respond(HttpStatusCode.OK, gym)
            }
        }
        get("/nearby") {
//            val lat = call.request.queryParameters["lat"]
//            val lng = call.request.queryParameters["lng"]
//            val radius = call.request.queryParameters["radius"]
            // TODO query database for nearby
            call.respondRedirect("/api/gyms", true)
        }
        get("/search") {
            val name = call.request.queryParameters["name"]
            if (name.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, ApiResponse("name query parameter is required"))
                return@get
            }

            call.respond(HttpStatusCode.OK, gymDao.searchByName("%$name%"))
        }
        post("/create") {
            val gym = call.receive<Gym>().copy(id = miniUUID())
            gymDao.insert(gym)
            call.respond(HttpStatusCode.Created, gym)
        }
        post("/update") {
            val gym = call.receive<Gym>()
            gymDao.update(gym)
            call.respond(HttpStatusCode.OK, gym)
        }

        route("/instructors") {
            post("/add") {
                call.respond(HttpStatusCode.NotImplemented, ApiResponse("Not implemented"))
            }
            post("/remove") {
                call.respond(HttpStatusCode.NotImplemented, ApiResponse("Not implemented"))
            }
        }
    }
}

