package com.dev.people.data.api

import com.dev.people.data.model.User
import retrofit2.Response
import javax.inject.Inject

/**
 * Created on 10 Dec 2022 by Sajid
 * API helper implementation class
 *
 */

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<List<User>> = apiService.getUsers()

}