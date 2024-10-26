package app.ditodev.ceritain.data.repository

import app.ditodev.ceritain.data.remote.api.ApiService

class LoginRepository(private val apiService: ApiService) {
    suspend fun handleLogin(email: String, password: String) {
        apiService.login(email = email, password = password)
    }

    companion object {
        @Volatile
        private var instance: LoginRepository? = null
        fun getInstance(apiService: ApiService): LoginRepository {
            return instance ?: synchronized(this) {
                instance
                    ?: LoginRepository(apiService).also { instance = it }
            }
        }
    }
}