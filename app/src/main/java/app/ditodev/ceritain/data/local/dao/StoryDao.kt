package app.ditodev.ceritain.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import app.ditodev.ceritain.data.local.entity.StoryEntity

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStory(story: List<StoryEntity>)
}