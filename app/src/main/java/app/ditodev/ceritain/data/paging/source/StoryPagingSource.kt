package app.ditodev.ceritain.data.paging.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.ditodev.ceritain.data.pref.UserPreferences
import app.ditodev.ceritain.data.remote.api.ApiService
import app.ditodev.ceritain.data.remote.response.ListStoryItem
import kotlinx.coroutines.flow.first

class StoryPagingSource(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) : PagingSource<Int, ListStoryItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val token = userPreferences.getToken().first()
            val response = apiService.getStories(
                "Bearer $token",
                page = page,
                size = params.loadSize
            )
            LoadResult.Page(
                data = response.listStory,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (response.listStory.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}