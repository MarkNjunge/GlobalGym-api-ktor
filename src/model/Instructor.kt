package com.marknjunge.model

import org.jdbi.v3.core.mapper.reflect.ColumnName

data class Instructor(
    @ColumnName("id")
    val id: String,
    @ColumnName("first_name")
    val firstName: String,
    @ColumnName("last_name")
    val lastName: String,
    @ColumnName("email")
    val email: String,
    @ColumnName("photo")
    val photo: String,
    @ColumnName("year_of_birth")
    val yearOfBirth: Int,
    @ColumnName("gender")
    val gender: Char,
    @ColumnName("country")
    val country: String,
    @ColumnName("gym")
    val gym: String?
)