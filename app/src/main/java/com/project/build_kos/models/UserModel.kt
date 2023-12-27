package com.project.build_kos.models

class UserModel {
    data class User (
        val id: String,
        val email: String,
        val password: String,
        val role: String
    )

    data class Profile (
        val id: String,
        val name: String,
        val phone: String,
        val address: String,
    )

    data class status (
        val status: String
    )
}
