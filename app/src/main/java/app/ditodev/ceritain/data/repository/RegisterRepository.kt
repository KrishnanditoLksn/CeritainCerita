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

    companion object {
        @Volatile
        private var instance: RegisterRepository? = null

        fun getInstance(apiService: ApiService): RegisterRepository {
            return instance ?: synchronized(this) {
                instance ?: RegisterRepository(apiService).also { instance = it }
            }
        }
    }
}