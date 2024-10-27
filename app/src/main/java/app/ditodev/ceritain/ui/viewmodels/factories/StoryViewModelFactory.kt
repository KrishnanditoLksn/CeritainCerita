package app.ditodev.ceritain.ui.viewmodels.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.ditodev.ceritain.data.di.DependencyInjection
import app.ditodev.ceritain.data.repository.StoryRepository
import app.ditodev.ceritain.ui.viewmodels.DisplayStoryViewModel
import app.ditodev.ceritain.ui.viewmodels.LoginViewModel
import app.ditodev.ceritain.ui.viewmodels.MainViewModel
import app.ditodev.ceritain.ui.viewmodels.RegisterViewModel

class StoryViewModelFactory(private val repository: StoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            modelClass.isAssignableFrom(DisplayStoryViewModel::class.java) -> {
                DisplayStoryViewModel(repository) as T
            }

            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: StoryViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): StoryViewModelFactory {
            if (INSTANCE == null) {
                synchronized(StoryViewModelFactory::class.java) {
                    INSTANCE = StoryViewModelFactory(DependencyInjection.provideRepository(context))
                }
            }
            return INSTANCE as StoryViewModelFactory
        }
    }
}