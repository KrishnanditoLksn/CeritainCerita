package app.ditodev.ceritain.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.ditodev.ceritain.data.remote.response.ListStoryItem
import app.ditodev.ceritain.data.repository.StoryRepository

class DisplayStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getStories() = repository.getStories()


    fun getStoriesPaging(): LiveData<PagingData<ListStoryItem>> {
        return repository.getStoriesPaging().cachedIn(viewModelScope)
    }
}