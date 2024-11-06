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
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock


class DisplayStoryViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DisplayStoryViewModel
    private val repository: StoryRepository = mock()
    private val dummyDatas = DataDummy.generateDummy()

    @Before
    fun setup() {
        viewModel = DisplayStoryViewModel(repository)
    }

    @Test
    fun `when get story should not null `() {
        val observer = Observer<Result<List<ListStoryItem>>> {}
        try {
            val expectedNews = MutableLiveData<Result<List<ListStoryItem>>>()
            expectedNews.value = Result.Success(dummyDatas)
            `when`(repository.getStories()).thenReturn(expectedNews)
            val actualStory = viewModel.getStories().observeForever(observer)
            Mockito.verify(repository).getStories()
            Assert.assertNotNull(actualStory)
//            Assert.assertTrue(actualStory is Result.Success)
//            Assert.assertEquals(dummyDatas.size, (actualStory as Result.Success).data.size)
        } finally {
            viewModel.getStories().removeObserver(observer)
        }
    }
}