package com.marknjunge.db

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.gson2.Gson2Plugin
import org.jdbi.v3.json.JsonPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin

class Database(url: String, username: String, password: String) {
    val gymDao: GymDao
    val userDao: UserDao
    val instructorsDao: InstructorsDao
    val sessionsDao: SessionsDao

    init {
        val jdbi = Jdbi.create(url, username, password)
            .installPlugin(KotlinSqlObjectPlugin())
            .installPlugin(SqlObjectPlugin())
            .installPlugin(KotlinPlugin())
            .installPlugin(PostgresPlugin())
            .installPlugin(JsonPlugin())
            .installPlugin(Gson2Plugin())

        gymDao = jdbi.onDemand(GymDao::class.java)
        gymDao.createTable()

        userDao = jdbi.onDemand(UserDao::class.java)
        userDao.createTable()

        instructorsDao = jdbi.onDemand(InstructorsDao::class.java)
        instructorsDao.createTable()

        sessionsDao = jdbi.onDemand(SessionsDao::class.java)
        sessionsDao.createTable()
    }
}