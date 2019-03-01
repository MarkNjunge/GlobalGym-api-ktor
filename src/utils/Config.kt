@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.marknjunge

import io.ktor.application.Application

class Config(application: Application) {
    val database = Database(application)
    val authKey = System.getenv("AUTH_KEY") ?: application.configVar("other.authKey")

    class Database(application: Application) {
        val url = System.getenv("DATABASE_URL") ?: application.configVar("database.url")
        val username = System.getenv("DATABASE_USERNAME") ?: application.configVar("database.username")
        val password = System.getenv("DATABASE_PASSWORD") ?: application.configVar("database.password")
    }
}

private fun Application.configVar(path: String): String {
    return environment.config.property(path).getString()
}
