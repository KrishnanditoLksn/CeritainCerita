package app.ditodev.ceritain.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.ditodev.ceritain.data.local.dao.StoryDao
import app.ditodev.ceritain.data.local.entity.StoryEntity

@Database(entities = [StoryEntity::class], version = 1, exportSchema = false)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase? = null

        fun getInstance(context: Context): StoryDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoryDatabase::class.java,
                    "story_db"
                ).build()
                INSTANCE = instance
                instance
            }
    }
}
