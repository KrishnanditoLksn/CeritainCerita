package app.ditodev.ceritain.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import app.ditodev.ceritain.data.remote.api.ApiService
import app.ditodev.ceritain.data.remote.response.ErrorResponse
import app.ditodev.ceritain.data.remote.response.RegisterResponse
import app.ditodev.ceritain.data.result.Result
import com.google.gson.Gson
import retrofit2.HttpException

class RegisterRepository(
    private val apiService: ApiService
) {
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