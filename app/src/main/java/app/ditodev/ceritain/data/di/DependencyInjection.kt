package app.ditodev.ceritain.data.di

import android.content.Context
import app.ditodev.ceritain.data.pref.UserPreferences
import app.ditodev.ceritain.data.pref.dataStore
import app.ditodev.ceritain.data.remote.api.ApiConfig
import app.ditodev.ceritain.data.repository.StoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object DependencyInjection {
    fun provideRepository(context: Context): StoryRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return StoryRepository.getInstance(apiService, pref)
    }
}