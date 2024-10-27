package app.ditodev.ceritain.ui.viewmodels

import androidx.lifecycle.ViewModel
import app.ditodev.ceritain.data.repository.StoryRepository

class RegisterViewModel(private val repository: StoryRepository) :
    ViewModel() {
    fun handleRegistration(
        name: String,
        email: String,
        password: String
    ) = repository.handleRegistration(name, email, password)
}