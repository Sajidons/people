package com.dev.people.data.api

import com.dev.people.data.model.User
import retrofit2.Response

/**
 * Created on 10 Dec 2022 by Sajid
 * ApiHelper interface
 *
 */

interface ApiHelper {

    suspend fun getUsers(): Response<List<User>>
}