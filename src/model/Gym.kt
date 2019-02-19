package com.marknjunge.model

import org.jdbi.v3.core.mapper.reflect.ColumnName

data class Gym(
    @ColumnName("id")
    val id: String,
    @ColumnName("name")
    val name: String,
    @ColumnName("logo")
    val logo: String,
    @ColumnName("phone")
    val phone: String,
    @ColumnName("website")
    val website: String,
    @ColumnName("open_time")
    val openTime: Int,
    @ColumnName("close_time")
    val closeTime: Int,
    @ColumnName("available")
    val available: Boolean,
    @ColumnName("country")
    val country: String,
    @ColumnName("city")
    val city: String,
    @ColumnName("cords_lat")
    val cordsLat: Float,
    @ColumnName("cords_lng")
    val cordsLng: Float,
    val images: List<String>? = listOf()
)