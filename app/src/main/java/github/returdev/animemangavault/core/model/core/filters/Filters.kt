package github.returdev.animemangavault.core.model.core.filters


/**
 * Data class representing filters for querying anime or manga.
 *
 * @property type The type of anime or manga.
 * @property status The status of anime or manga.
 * @property genres The list of genre IDs.
 * @property orderBy The property used for ordering.
 * @property sort The direction of sorting (ascending or descending).
 */
data class Filters (
    val type : String? = null,
    val status : String? = null,
    val genres : List<Int> = emptyList(),
    val demographic : List<Int> = emptyList(),
    val orderBy : String? = null,
    val sort : SortDirection = SortDirection.ASCENDANT
)