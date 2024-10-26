package app.ditodev.ceritain.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import app.ditodev.ceritain.data.model.UserModel
import app.ditodev.ceritain.data.pref.UserPreferences
import app.ditodev.ceritain.data.remote.api.ApiService
import app.ditodev.ceritain.data.remote.response.ErrorResponse
import app.ditodev.ceritain.data.remote.response.LoginResponse
import app.ditodev.ceritain.data.remote.response.RegisterResponse
import app.ditodev.ceritain.data.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class StoryRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreferences
) {
    //register user
    fun handleRegistration(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name = name, email = email, password = password)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    //login
    fun handleLogin(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email = email, password = password)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreferences
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, userPreference)
            }.also { instance = it }
    }
}