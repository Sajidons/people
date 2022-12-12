package com.dev.people.data.api

import com.dev.people.data.model.User
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created on 10 Dec 2022 by Sajid
 * Interface for listing of APIs
 *
 */

interface ApiService {

    @GET("teams/users")
    suspend fun getUsers(): Response<List<User>>

}