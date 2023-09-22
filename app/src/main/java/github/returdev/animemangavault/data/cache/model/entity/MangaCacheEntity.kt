package github.returdev.animemangavault.data.cache.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga_cache")
data class MangaCacheEntity(
    @PrimaryKey(autoGenerate = true) override val order: Long = 0,
    @ColumnInfo(name = "id") override val id: Int,
    @ColumnInfo(name = "images")override val images: List<String>,
    @ColumnInfo(name = "title")override val title: String,
    @ColumnInfo(name = "score")override val score: Float,
    @ColumnInfo(name = "type")override val type: String,
    @ColumnInfo(name = "genres")override val genres: List<String>,
    @ColumnInfo(name = "demographics")override val demographics: List<String>
) : VisualMediaCache()