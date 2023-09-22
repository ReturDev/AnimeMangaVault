package github.returdev.animemangavault.data.cache.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import github.returdev.animemangavault.data.cache.model.entity.MangaCacheEntity

@Dao
interface MangaCacheDao {

    /**
     * Insert a list of [MangaCacheEntity] objects into the "manga_cache" table.
     *
     * @param items The list of [MangaCacheEntity] objects to be inserted.
     */
    @Insert
    fun insertPage(items: List<MangaCacheEntity>)


    /**
     * Retrieves the manga data from the "manga_cache" table.
     *
     * @return A [PagingSource] containing the manga data with pagination support.
     */
    @Query("SELECT * FROM manga_cache")
    fun getManga(): PagingSource<Int, MangaCacheEntity>

    /**
     * Clear all data from the manga_cache table.
     */
    @Query("DELETE FROM manga_cache")
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