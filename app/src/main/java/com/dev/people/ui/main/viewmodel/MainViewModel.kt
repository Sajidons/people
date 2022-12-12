package com.dev.people.ui.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.dev.people.data.model.User
import com.dev.people.data.repository.MainRepository
import com.dev.people.utils.NetworkHelper
import com.dev.people.utils.Resource
import kotlinx.coroutines.launch

/**
 * Created on 10 Dec 2022 by Sajid
 * View model kotlin class for Users
 *
 */

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _users = MutableLiveData<Resource<List<User>>>()
    val users: LiveData<Resource<List<User>>>
        get() = _users

    init {
        fetchUsers()
    }

    //fetch users from api using coroutine
    private fun fetchUsers() {

        viewModelScope.launch {
            _users.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getUsers().let {
                    if (it.isSuccessful) {
                        print("fetchUsers"+ it.body())
                        _users.postValue(Resource.success(it.body()))
                    } else _users.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _users.postValue(Resource.error("No internet connection", null))
        }
    }
}