package com.dev.people.data.model

import com.squareup.moshi.Json

/**
 * Created on 10 Dec 2022 by Sajid
 * User data object
 *
 */

data class User(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "avatar")
    val avatar: String = ""
)