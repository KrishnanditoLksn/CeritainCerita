package app.ditodev.ceritain.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import app.ditodev.ceritain.data.model.UserModel
import app.ditodev.ceritain.data.pref.UserPreferences
import app.ditodev.ceritain.data.remote.api.ApiService
import app.ditodev.ceritain.data.remote.response.ErrorResponse
import app.ditodev.ceritain.data.remote.response.ListStoryItem
import app.ditodev.ceritain.data.remote.response.LoginResponse
import app.ditodev.ceritain.data.remote.response.RegisterResponse
import app.ditodev.ceritain.data.remote.response.UploadStoryResponse
import app.ditodev.ceritain.data.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
            userPreference.saveSession(
                UserModel(
                    userId = response.loginResult?.userId ?: "",
                    name = response.loginResult?.name ?: "",
                    token = response.loginResult?.token ?: ""
                )
            )
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    //display list of stories
    fun getStories(): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val token = userPreference.getToken().first()
            val response = apiService.getStories("Bearer $token")
            val stories = response.listStory
            emit(Result.Success(stories))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun getStoriesById(id: String): LiveData<Result<ListStoryItem>> = liveData {
        emit(Result.Loading)
        try {
            val token = userPreference.getToken().first()
            val response = apiService.getStoriesById(id, "Bearer $token")
            val story = response.story ?: ListStoryItem(
                photoUrl = "",
                createdAt = "",
                name = "",
                description = "",
                lon = 0.0,
                id = "",
                lat = 0.0
            )
            emit(Result.Success(story))
        } catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun uploadStory(
        file: MultipartBody.Part,
        description: RequestBody
    ): LiveData<Result<UploadStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val token = userPreference.getToken().first()
            val response =
                apiService.uploadStory(file, description, "Bearer $token")
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
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