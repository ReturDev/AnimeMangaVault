package github.returdev.animemangavault.data.library.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Entity class that represents a manga entity in the library.
 *
 * @property id The unique identifier of the manga.
 * @property imageUrl The URL of the manga's image.
 * @property defaultTitle The default title of the manga.
 * @property score The score of the manga.
 * @property state The state of the manga in the library.
 * @property addedDate The date when the manga was added to the library.
 */
@Entity("library_manga")
data class MangaLibraryEntity (
    @PrimaryKey val id : Int,
    @ColumnInfo("image_url") val imageUrl : String,
    @ColumnInfo("default_title") val defaultTitle : String,
    @ColumnInfo("score") val score : Float,
    @ColumnInfo("state") val state : String,
    @ColumnInfo("added_date") val addedDate : Date
)