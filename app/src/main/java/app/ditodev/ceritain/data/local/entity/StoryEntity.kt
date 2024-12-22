package app.ditodev.ceritain.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story")
data class StoryEntity(
    @PrimaryKey(autoGenerate = false) val uid: Int,
    @ColumnInfo(name = "story_name") val storyName: String,
    @ColumnInfo(name = "description_story") val description: String,
    @ColumnInfo(name = "photoUrl") val photoUrl: String,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lon") val lon: Double,
)