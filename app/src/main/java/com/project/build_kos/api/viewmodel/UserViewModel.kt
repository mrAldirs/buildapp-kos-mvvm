package com.project.build_kos.api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.build_kos.api.repository.UserRepository
import com.project.build_kos.models.UserModel

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository()

    fun registerUser(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        userRepository.createUserWithEmailAndPassword(email, password, onComplete)
    }

    fun createUser(user: UserModel.User): LiveData<Boolean> {
        return userRepository.create(user)
    }

    fun createSingle(data: UserModel.Profile, coll: String): LiveData<Boolean> {
        return userRepository.createSingle(data, coll)
    }
}