package app.ditodev.ceritain.utils

import app.ditodev.ceritain.data.remote.response.ListStoryItem

object DataDummy {
    fun generateDummy(): List<ListStoryItem> {
        val storyList = ArrayList<ListStoryItem>()

        for (i in 0..10) {
            val story = ListStoryItem(
                id = "$i",
                name = "Name $i",
                description = "Description $i",
                photoUrl = "https://picsum.photos/200/300",
                createdAt = "2021-08-01"
            )
            storyList.add(story)
        }
        return storyList
    }

    fun generateDummyObj(): ListStoryItem {
        return ListStoryItem(
            id = "1",
            name = "Joko",
            description = "Description",
            photoUrl = "https://picsum.photos/200/300",
            createdAt = "2021-08-01"
        )
    }
}