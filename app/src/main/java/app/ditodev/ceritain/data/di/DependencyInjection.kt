package app.ditodev.ceritain.data.di

import android.content.Context
import app.ditodev.ceritain.data.pref.UserPreferences
import app.ditodev.ceritain.data.pref.dataStore
import app.ditodev.ceritain.data.remote.api.ApiConfig
import app.ditodev.ceritain.data.repository.StoryRepository

object DependencyInjection {
    fun provideRepository(context: Context): StoryRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstance(apiService, pref)
    }
}