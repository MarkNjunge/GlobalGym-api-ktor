package model

import org.jdbi.v3.core.mapper.reflect.ColumnName

data class GymImage(
    @ColumnName("gym_id")
    val id: String,
    val url: String
)