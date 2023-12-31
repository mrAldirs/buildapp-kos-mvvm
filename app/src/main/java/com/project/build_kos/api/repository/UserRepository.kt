package com.project.build_kos.api.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.build_davina.api.global.Config
import com.project.build_davina.api.global.Data
import com.google.firebase.auth.UserProfileChangeRequest
import com.project.build_kos.models.UserModel
import kotlin.collections.HashMap

class UserRepository {
    val auth = Config.firebaseAuth
    val firestore = Config.firestore

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val authId = auth.currentUser?.uid
                    if (user != null) {
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .build()
                        user.updateProfile(profileUpdates)
                        user.sendEmailVerification()
                        onComplete(true,authId)
                    } else {
                        onComplete(false,null)
                    }
                } else {
                    onComplete(false,null)
                }
            }
    }

    fun create(user: UserModel.User): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val hm = HashMap<String,String>()
        hm.set("id", user.id)
        hm.set("email", user.email)
        hm.set("password", user.password)
        hm.set("role", user.role)

        firestore.collection(Data.user)
            .document(user.id)
            .set(hm)
            .addOnSuccessListener {
                result.value = true
            }
            .addOnFailureListener {
                result.value = false
            }

        return result
    }

    fun createSingle(data: UserModel.Profile, coll: String) : LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val hm = HashMap<String,String>()
        hm.set("id", data.id)
        hm.set("name", data.name)
        hm.set("address", data.address)
        hm.set("phone", data.phone)

        firestore.collection(coll)
            .document(data.id)
            .set(hm)
            .addOnSuccessListener {
                result.value = true
            }
            .addOnFailureListener {
                result.value = false
            }

        return result
    }
}