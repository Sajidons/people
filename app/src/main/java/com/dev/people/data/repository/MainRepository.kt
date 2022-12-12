package com.dev.people.data.repository

import com.dev.people.data.api.ApiHelper
import javax.inject.Inject

/**
 * Created on 10 Dec 2022 by Sajid
 * MainRepository for communicate the data with MainViewModel
 *
 */

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getUsers() =  apiHelper.getUsers()

}