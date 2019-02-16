package com.marknjunge

import io.ktor.application.Application

class Config(application: Application) {
    val database = Database(application)

    class Database(application: Application) {
        val url = application.configVar("database.url")
        val username = application.configVar("database.username")
        val password = application.configVar("database.password")
    }
}

fun Application.configVar(path: String): String {
    return environment.config.property(path).getString()
}
