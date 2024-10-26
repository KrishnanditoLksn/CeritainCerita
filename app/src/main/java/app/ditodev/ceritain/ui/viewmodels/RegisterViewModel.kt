package app.ditodev.ceritain.ui.viewmodels

import androidx.lifecycle.ViewModel
import app.ditodev.ceritain.data.repository.RegisterRepository

class RegisterViewModel(private val repository: RegisterRepository) :
    ViewModel() {
    fun handleRegistration(
        name: String,
        email: String,
        password: String
    ) = repository.handleRegistration(name, email, password)
}