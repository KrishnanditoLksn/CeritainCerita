package app.ditodev.ceritain.ui.viewmodels

import androidx.core.util.PatternsCompat
import androidx.lifecycle.ViewModel
import app.ditodev.ceritain.data.repository.StoryRepository

class RegisterViewModel(private val repository: StoryRepository) :
    ViewModel() {
    fun handleRegistration(
        name: String,
        email: String,
        password: String
    ) = repository.handleRegistration(name, email, password)

    fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    fun isNameValid(uName: String): Boolean {
        return uName.isNotEmpty()
    }

    fun isEmailValid(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isFormValid(name: String, email: String, password: String): Boolean {
        return isNameValid(name) && isEmailValid(email) && isPasswordValid(password)
    }

}