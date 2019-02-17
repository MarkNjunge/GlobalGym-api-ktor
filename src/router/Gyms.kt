package com.marknjunge.router

import com.marknjunge.db.GymDao
import com.marknjunge.miniUUID
import com.marknjunge.model.ApiResponse
import com.marknjunge.model.Gym
import com.marknjunge.utils.ItemNotFoundException
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import model.GymImage

fun Route.gyms(gymDao: GymDao) {
    gymDao.createTable()
    gymDao.createImagesTable()

    route("/gyms") {
        get("/") {
            // Convert the list of GymImage to a map of gymId, imagesUrl
            val imageMap: MutableMap<String, MutableList<String>> = mutableMapOf()
            gymDao.selectAllImages().forEach { gymImage ->
                if (imageMap[gymImage.id] == null) {
                    imageMap[gymImage.id] = mutableListOf()
                }
                imageMap[gymImage.id]!!.add(gymImage.url)
            }

            val gyms = gymDao.selectAll().map { gym ->
                gym.copy(images = imageMap[gym.id] ?: mutableListOf())
            }
            call.respond(HttpStatusCode.OK, gyms)
        }
        get("/{id}") {
            val id = call.parameters["id"]
            if (id.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, ApiResponse("id path parameter is required"))
                return@get
            }

            val gym = gymDao.selectById(id)
            if (gym == null) {
                throw ItemNotFoundException("There is no gym with id $id")
            } else {
                val imagesForGym = gymDao.selectImagesForGym(id)
                call.respond(HttpStatusCode.OK, gym.copy(images = imagesForGym))
            }
        }
        get("/nearby") {
            val lat = call.request.queryParameters["lat"]
            val lng = call.request.queryParameters["lng"]
            val radius = call.request.queryParameters["radius"]
            call.respond(HttpStatusCode.NotImplemented, ApiResponse("Not implemented. $lat, $lng, $radius"))
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
        route("/images") {
            post("/add") {
                val (id, url) = call.receive<GymImage>()
                gymDao.insertImage(id, url)

                call.respond(HttpStatusCode.OK, ApiResponse("Image added"))
            }
            delete("/remove") {
                val (id, url) = call.receive<GymImage>()
                gymDao.removeImage(id, url)

                call.respond(HttpStatusCode.OK, ApiResponse("Image removed"))
            }
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

