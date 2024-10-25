package app.ditodev.ceritain.data.di

import app.ditodev.ceritain.data.remote.api.ApiConfig
import app.ditodev.ceritain.data.repository.RegisterRepository

object DependencyInjection {
    fun provideRegisterRepository(): RegisterRepository {
        val apiService = ApiConfig.getApiService()
        return RegisterRepository.getInstance(apiService)
    }
}