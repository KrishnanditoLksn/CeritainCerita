package app.ditodev.ceritain.ui.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.ditodev.ceritain.data.di.DependencyInjection
import app.ditodev.ceritain.data.repository.RegisterRepository
import app.ditodev.ceritain.ui.viewmodels.RegisterViewModel

class RegisterViewModelFactory private constructor(private val repository: RegisterRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel Class " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: RegisterViewModelFactory? = null
        fun getInstance(): RegisterViewModelFactory =
            instance ?: synchronized(this) {
                instance
                    ?: RegisterViewModelFactory(DependencyInjection.provideRegisterRepository())
            }.also { instance = it }
    }
}