package com.marknjunge.db

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin

class Database(url: String, username: String, password: String) {
    val gymDao: GymDao

    init {
        val jdbi = Jdbi.create(url, username, password)
            .installPlugin(KotlinSqlObjectPlugin())
            .installPlugin(SqlObjectPlugin())
            .installPlugin(KotlinPlugin())
            .installPlugin(PostgresPlugin())

        gymDao = jdbi.onDemand(GymDao::class.java)
    }
}