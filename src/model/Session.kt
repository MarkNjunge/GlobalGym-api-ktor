package com.marknjunge.model

import org.jdbi.v3.core.mapper.reflect.ColumnName
import org.jdbi.v3.json.Json

data class Session(
    @ColumnName("id")
    val id: String,
    @ColumnName("name")
    val name: String,
    @ColumnName("user_id")
    val user: String,
    @ColumnName("session_date")
    val date: Long,
    @ColumnName("gym")
    val gym: String,
    @ColumnName("completed")
    val complete: Boolean,
    @ColumnName("steps")
    @Json
    val steps: List<SessionStep>
)