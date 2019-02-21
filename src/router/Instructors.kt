package com.marknjunge.router

import com.marknjunge.db.GymDao
import com.marknjunge.db.InstructorsDao
import com.marknjunge.utils.miniUUID
import com.marknjunge.model.ApiResponse
import com.marknjunge.model.Instructor
import com.marknjunge.utils.ItemNotFoundException
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.instructors(instructorsDao: InstructorsDao, gymDao: GymDao) {
    route("/instructors") {
        get("/") {
            call.respond(HttpStatusCode.OK, instructorsDao.selectAll())
        }
        get("/{id}") {
            val id = call.parameters["id"]!!

            val instructor =
                instructorsDao.selectById(id) ?: throw ItemNotFoundException("There is no instructor with id $id")
            call.respond(HttpStatusCode.OK, instructor)
        }
        get("/{id}/gym") {
            val id = call.parameters["id"]!!

            val instructor =
                instructorsDao.selectById(id) ?: throw ItemNotFoundException("There is no instructor with id $id")

            if (instructor.gym == null){
                call.respond(HttpStatusCode.NoContent)
                return@get
            }

            val gym =
                gymDao.selectById(instructor.gym)
                    ?: throw ItemNotFoundException("There was a problem getting the instructor's gym")
            call.respond(HttpStatusCode.OK, gym)
        }
        post("/{id}/gym/add") {
            val params = call.receive<Map<String, String>>()
            val instructorId = call.parameters["id"]
            val gymId = params["gymId"]

            if (gymId == null) {
                call.respond(HttpStatusCode.BadRequest, "gymId is required")
                return@post
            }

            instructorsDao.setGym(instructorId!!, gymId)

            call.respond(HttpStatusCode.OK, ApiResponse("Gym added"))
        }
        post("/{id}/gym/remove") {
            val instructorId = call.parameters["id"]!!
            instructorsDao.removeGym(instructorId)
            call.respond(HttpStatusCode.OK, ApiResponse("Gym removed"))
        }
        post("/create") {
            val instructor = call.receive<Instructor>().copy(id = miniUUID())
            instructorsDao.insert(instructor)
            call.respond(HttpStatusCode.Created, instructor)
        }
        post("/update") {
            val instructor = call.receive<Instructor>().copy(id = miniUUID())
            instructorsDao.update(instructor)
            call.respond(HttpStatusCode.OK, instructor)
        }
    }
}