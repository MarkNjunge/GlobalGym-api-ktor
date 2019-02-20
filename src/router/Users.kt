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

fun Route.users(userDao: UserDao, gymDao: GymDao) {

    route("/users") {
        get("/") {
            // Convert the list of GymImage to a map of gymId, imagesUrl
            val imageMap: MutableMap<String, MutableList<String>> = mutableMapOf()
            gymDao.selectAllImages().forEach { gymImage ->
                if (imageMap[gymImage.id] == null) {
                    imageMap[gymImage.id] = mutableListOf()
                }
                imageMap[gymImage.id]!!.add(gymImage.url)
            }

            val users = userDao.selectAll().map { user ->
                user.preferredGym?.let { gym ->
                    user.copy(preferredGym = user.preferredGym.copy(images = imageMap[gym.id] ?: mutableListOf()))
                } ?: user
            }

            call.respond(HttpStatusCode.OK, users)
        }
        get("/{id}") {
            val id = call.parameters["id"]!!

            val user = userDao.selectById(id)
            if (user == null) {
                throw ItemNotFoundException("There is no user with id $id")
            } else {
                val gym = user.preferredGym?.let { gym ->
                    val imagesForGym = gymDao.selectImagesForGym(gym.id)
                    gym.copy(images = imagesForGym)
                }
                call.respond(HttpStatusCode.OK, user.copy(preferredGym = gym))
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