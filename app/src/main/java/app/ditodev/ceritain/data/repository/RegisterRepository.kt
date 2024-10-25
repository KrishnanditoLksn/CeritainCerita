package app.ditodev.ceritain.data.repository

import app.ditodev.ceritain.data.remote.api.ApiService

class RegisterRepository private constructor(
    private val apiService: ApiService
) {
    suspend fun handleRegistration(
        name: String,
        email: String,
        password: String
    ) {
        apiService.register(
            name = name,
            email = email,
            password = password
        )
    }
}