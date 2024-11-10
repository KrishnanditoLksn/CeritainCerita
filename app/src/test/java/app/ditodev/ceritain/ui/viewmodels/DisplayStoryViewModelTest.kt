package app.ditodev.ceritain.ui.viewmodels

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import app.ditodev.ceritain.data.remote.response.ListStoryItem
import app.ditodev.ceritain.data.repository.StoryRepository
import app.ditodev.ceritain.getOrAwaitValue
import app.ditodev.ceritain.ui.adapter.StoriesListAdapter
import app.ditodev.ceritain.utils.DataDummy
import app.ditodev.ceritain.utils.MainDispatcherRules
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DisplayStoryViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRules()

    @Mock
    private lateinit var storyRepository: StoryRepository


    private lateinit var storyviewModel: DisplayStoryViewModel
    private val dummyDatas = DataDummy.generateDummy()
    private lateinit var mockedLog: MockedStatic<Log>

    @Before
    fun setup() {
        mockedLog = Mockito.mockStatic(Log::class.java)
        mockedLog.`when`<Boolean>
        { Log.isLoggable(Mockito.anyString(), Mockito.anyInt()) }
            .thenReturn(false)
        storyviewModel = DisplayStoryViewModel(storyRepository)
    }

    @Test
    fun `when get story should not null  and return data`() = runTest {
        val data: PagingData<ListStoryItem> = StoryPagingSource.snapshot(dummyDatas)
        val expectedData = MutableLiveData<PagingData<ListStoryItem>>()
        expectedData.value = data
        Mockito.`when`(storyRepository.getStoriesPaging()).thenReturn(expectedData)

        val actualStory: PagingData<ListStoryItem> =
            storyviewModel.getStoriesPaging().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesListAdapter.DIFF_CALLBACK,
            updateCallback = noopListCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyDatas.size, differ.snapshot().size)
        Assert.assertEquals(dummyDatas[0], differ.snapshot()[0])
    }

    @Test
    fun `when get story should not null and return data`() = runTest {
        val data: PagingData<ListStoryItem> = PagingData.from(emptyList())
        val expectedData = MutableLiveData<PagingData<ListStoryItem>>()
        expectedData.value = data

        Mockito.`when`(storyRepository.getStoriesPaging()).thenReturn(expectedData)
        val actualStory: PagingData<ListStoryItem> =
            storyviewModel.getStoriesPaging().getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesListAdapter.DIFF_CALLBACK,
            updateCallback = noopListCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)

        Assert.assertEquals(0, differ.snapshot().size)
    }


    private val noopListCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {

        }

        override fun onRemoved(position: Int, count: Int) {

        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {

        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {

        }
    }

    @After
    fun resetMockito() {
        mockedLog.close()
    }
}

class StoryPagingSource : PagingSource<Int, LiveData<List<ListStoryItem>>>() {
    companion object {
        fun snapshot(items: List<ListStoryItem>): PagingData<ListStoryItem> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStoryItem>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStoryItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}