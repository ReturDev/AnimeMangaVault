package github.returdev.animemangavault.data.library.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Entity class that represents an anime entity in the library.
 *
 * @property id The unique identifier of the anime.
 * @property imageUrl The URL of the anime's image.
 * @property defaultTitle The default title of the anime.
 * @property score The score of the anime.
 * @property state The state of the anime in the library.
 * @property addedDate The date when the anime was added to the library.
 */
@Entity("library_anime")
data class AnimeLibraryEntity (
    @PrimaryKey val id : Int,
    @ColumnInfo("image_url") val imageUrl : String,
    @ColumnInfo("default_title") val defaultTitle : String,
    @ColumnInfo("score") val score : Float,
    @ColumnInfo("state") val state : String,
    @ColumnInfo("added_date") val addedDate : Date
)