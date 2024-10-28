package app.ditodev.ceritain.ui.viewmodels

import androidx.lifecycle.ViewModel
import app.ditodev.ceritain.data.repository.StoryRepository

class DetailStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getStories(id: String) = repository.getStoriesById(id)
}