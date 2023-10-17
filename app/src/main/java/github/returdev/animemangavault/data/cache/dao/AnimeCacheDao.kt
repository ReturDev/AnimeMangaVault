package github.returdev.animemangavault.data.cache.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import github.returdev.animemangavault.data.cache.model.entity.AnimeCacheEntity

@Dao
interface AnimeCacheDao  {

    /**
     * Inserts a list of [AnimeCacheEntity] objects into the database.
     *
     * @param items The list of [AnimeCacheEntity] objects to be inserted.
     */
    @Insert
    fun insertPage(items: List<AnimeCacheEntity>)

    /**
     * Retrieves the anime data from the anime_cache table.
     *
     * @return A [PagingSource] containing the anime data with pagination support.
     */
    @Query("SELECT * FROM anime_cache ORDER BY `order`")
    fun getAnime(): PagingSource<Int, AnimeCacheEntity>

    /**
     * Clear all data from the manga_cache table.
     */
    @Query("DELETE FROM anime_cache")
    fun clearTable()

    /**
     * Executes a raw SQLite query and returns the result as an integer value.
     *
     * @param query The raw SQLite query to execute.
     * @return The result of the query as an integer value.
     */
    @RawQuery
    fun executeRawQuery(query: SupportSQLiteQuery) : Int

}