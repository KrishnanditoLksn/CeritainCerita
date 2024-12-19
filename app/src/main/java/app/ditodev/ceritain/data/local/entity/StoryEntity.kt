package app.ditodev.ceritain.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class StoryEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "story_name") val storyName: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "created_at") val createdAt: Date
)