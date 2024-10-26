package app.ditodev.ceritain.ui.viewmodels

import androidx.lifecycle.ViewModel
import app.ditodev.ceritain.data.repository.LoginRepository

class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel() {
    fun handleLogin(email: String, password: String) = repository.handleLogin(email, password)
}