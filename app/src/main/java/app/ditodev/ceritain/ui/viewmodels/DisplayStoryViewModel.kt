package app.ditodev.ceritain.ui.viewmodels

import androidx.lifecycle.ViewModel
import app.ditodev.ceritain.data.repository.StoryRepository

class DisplayStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getStories() = repository.getStories()
}