@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.marknjunge.utils

import io.ktor.application.Application

class Config(application: Application) {
    val database = Database(application)
    val authKey = application.envOrConfigVar("AUTH_KEY", "other.authKey")

    class Database(application: Application) {
        val url = application.envOrConfigVar("DATABASE_URL", "database.url")
        val username = application.envOrConfigVar("DATABASE_USERNAME", "database.username")
        val password = application.envOrConfigVar("DATABASE_PASSWORD", "database.password")
    }
}

private fun Application.envOrConfigVar(env: String, path: String) =
    System.getenv(env) ?: environment.config.property(path).getString()
