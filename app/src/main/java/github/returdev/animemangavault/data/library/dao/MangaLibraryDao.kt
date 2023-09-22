package github.returdev.animemangavault.data.library.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import github.returdev.animemangavault.data.library.model.entity.MangaLibraryEntity

/**
 * Data Access Object (DAO) interface for accessing and manipulating the library manga data.
 */
@Dao
interface MangaLibraryDao {

    /**
     * Retrieves a [PagingSource] of manga items from the library based on the provided state and ordering.
     *
     * @param state The state of the library visual media.
     * @param orderBy The ordering criteria (1: added date, 2: default title, 3: score).
     * @return A paging source containing the manga items.
     */
    @Query("SELECT *" +
            "FROM library_manga " +
            "WHERE state = :state " +
            "ORDER BY " +
            "CASE WHEN :orderBy = 1 THEN added_date END, " +
            "CASE WHEN :orderBy = 2 THEN default_title END, " +
            "CASE WHEN :orderBy = 3 THEN score END ")
    fun getMangasByState(state : String, orderBy : Int) : PagingSource<Int, MangaLibraryEntity>


    /**
     * Retrieves a [PagingSource] of manga items from the library based on the provided state in descending order.
     *
     * @param state The state of the library visual media.
     * @param orderBy The ordering criteria (1: added date, 2: default title, 3: score).
     * @return A paging source containing the manga items in descending order.
     */
    @Query("SELECT *" +
            "FROM library_manga " +
            "WHERE state = :state " +
            "ORDER BY " +
            "CASE WHEN :orderBy = 1 THEN added_date END DESC,  " +
            "CASE WHEN :orderBy = 2 THEN default_title END DESC, " +
            "CASE WHEN :orderBy = 3 THEN score END DESC ")
    fun getMangasByStateDesc(state : String, orderBy : Int) : PagingSource<Int, MangaLibraryEntity>


    /**
     * Retrieves the state of a manga item based on its ID.
     *
     * @param id The ID of the manga item.
     * @return The state of the manga item.
     */
    @Query("SELECT state FROM library_manga WHERE id = :id")
    fun getMangaStateById(id : Int) : String?


    /**
     * Inserts a new manga item into the library.
     *
     * @param manga The manga entity to be inserted.
     */
    @Insert
    fun insertManga(manga : MangaLibraryEntity)

    /**
     * Deletes a manga item from the library based on its ID.
     *
     * @param id The ID of the manga item to be deleted.
     * @return The number of rows affected.
     */
    @Query("DELETE FROM library_manga WHERE id = :id")
    fun deleteManga(id : Int) : Int

    /**
     * Updates the state of a manga item based on its ID.
     *
     * @param id The ID of the manga item to be updated.
     * @param newState The new state to be assigned to the manga item.
     * @return The number of rows affected.
     */
    @Query("UPDATE library_manga SET state = :newState WHERE id = :id")
    fun updateMangaState(id : Int, newState : String) : Int


}