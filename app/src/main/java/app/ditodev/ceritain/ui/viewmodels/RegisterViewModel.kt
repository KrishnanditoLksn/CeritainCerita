package app.ditodev.ceritain.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ditodev.ceritain.data.repository.RegisterRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: RegisterRepository) :
    ViewModel() {
    fun handleRegistration(
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            repository.handleRegistration(
                name = name,
                email = email,
                password = password
            )
        }
    }
}