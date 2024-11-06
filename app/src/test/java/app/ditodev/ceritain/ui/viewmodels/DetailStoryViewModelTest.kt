package app.ditodev.ceritain.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import app.ditodev.ceritain.data.remote.response.ListStoryItem
import app.ditodev.ceritain.data.repository.StoryRepository
import app.ditodev.ceritain.data.result.Result
import app.ditodev.ceritain.utils.DataDummy
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.mock


class DetailStoryViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var vm: DetailStoryViewModel
    private val repository: StoryRepository = mock()
    private val dataDummy = DataDummy.generateDummyObj()


    @Before
    fun setup() {
        vm = DetailStoryViewModel(repository)
    }

    @Test
    fun getStories() {
        val observer = Observer<Result<ListStoryItem>> {}
        try {
            val expectedNews = MutableLiveData<Result<ListStoryItem>>()
            expectedNews.value = Result.Success(dataDummy)
            Mockito.`when`(repository.getStoriesById("1")).thenReturn(expectedNews)
            val actualStory = vm.getStories("1").observeForever(observer)
            Mockito.verify(repository).getStoriesById("1")
            Assert.assertNotNull(actualStory)
        } finally {
            vm.getStories("1").removeObserver(observer)
        }
    }
}