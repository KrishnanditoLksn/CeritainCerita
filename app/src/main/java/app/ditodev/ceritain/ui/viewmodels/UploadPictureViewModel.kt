package app.ditodev.ceritain.ui.viewmodels

import androidx.lifecycle.ViewModel
import app.ditodev.ceritain.data.repository.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadPictureViewModel(private val repository: StoryRepository) : ViewModel() {
    fun uploadStory(story: MultipartBody.Part, description: RequestBody) =
        repository.uploadStory(story, description)
}