package app.ditodev.ceritain.ui.viewmodels

import androidx.lifecycle.ViewModel
import app.ditodev.ceritain.data.repository.StoryRepository

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getStoriesWithLocation() = repository.getStoriesWithLocation()
}