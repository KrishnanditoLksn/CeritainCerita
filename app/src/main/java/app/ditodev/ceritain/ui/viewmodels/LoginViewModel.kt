package app.ditodev.ceritain.ui.viewmodels

import androidx.lifecycle.ViewModel
import app.ditodev.ceritain.data.repository.StoryRepository

class LoginViewModel(
    private val repository: StoryRepository
) : ViewModel() {
    fun handleLogin(email: String, password: String) = repository.handleLogin(email, password)
}