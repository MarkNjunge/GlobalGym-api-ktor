package com.marknjunge

import io.ktor.application.Application

class Config(application: Application) {
    val database = Database(application)

    class Database(application: Application) {
        val url = System.getenv("DATABASE_URL") ?: application.configVar("database.url")
        val username = System.getenv("DATABASE_USERNAME") ?: application.configVar("database.username")
        val password = System.getenv("DATABASE_PASSWORD") ?: application.configVar("database.password")
    }
}

fun Application.configVar(path: String): String {
    return environment.config.property(path).getString()
}
