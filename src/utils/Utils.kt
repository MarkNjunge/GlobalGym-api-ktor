package com.marknjunge.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

fun miniUUID() = UUID.randomUUID().toString().split("-")[0]

inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)