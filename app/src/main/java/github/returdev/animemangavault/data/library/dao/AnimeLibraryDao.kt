package github.returdev.animemangavault.data.library.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import github.returdev.animemangavault.data.library.model.entity.AnimeLibraryEntity

/**
 * Data Access Object (DAO) interface for accessing and manipulating the library anime data.
 */
@Dao
interface AnimeLibraryDao {

    /**
     * Retrieves a [PagingSource] of anime items from the library based on the provided state and ordering.
     *
     * @param state The state of the library visual media.
     * @param orderBy The ordering criteria (1: added date, 2: default title, 3: score).
     * @return A paging source containing the anime items.
     */
    @Query("SELECT *" +
            "FROM library_anime " +
            "WHERE state = :state " +
            "ORDER BY " +
            "CASE WHEN :orderBy = 1 THEN added_date END, " +
            "CASE WHEN :orderBy = 2 THEN default_title END, " +
            "CASE WHEN :orderBy = 3 THEN score END ")
    fun getAnimesByState(state : String, orderBy : Int) : PagingSource<Int, AnimeLibraryEntity>


    /**
     * Retrieves a [PagingSource] of anime items from the library based on the provided state in descending order.
     *
     * @param state The state of the library visual media.
     * @param orderBy The ordering criteria (1: added date, 2: default title, 3: score).
     * @return A paging source containing the anime items in descending order.
     */
    @Query("SELECT *" +
            "FROM library_anime " +
            "WHERE state = :state " +
            "ORDER BY " +
            "CASE WHEN :orderBy = 1 THEN added_date END DESC," +
            "CASE WHEN :orderBy = 2 THEN default_title END DESC, " +
            "CASE WHEN :orderBy = 3 THEN score END DESC ")
    fun getAnimeByStateDesc(state : String, orderBy : Int) : PagingSource<Int, AnimeLibraryEntity>


    /**
     * Retrieves the state of an anime item based on its ID.
     *
     * @param id The ID of the anime item.
     * @return The state of the anime item.
     */
    @Query("SELECT state FROM library_anime WHERE id = :id")
    fun getAnimeStateById(id : Int) : String?

    /**
     * Inserts a new anime item into the library.
     *
     * @param anime The anime entity to be inserted.
     */
    @Insert
    fun insertAnime(anime : AnimeLibraryEntity)



    /**
     * Deletes an anime item from the library based on its ID.
     *
     * @param id The ID of the anime item to be deleted.
     * @return The number of rows affected.
     */
    @Query("DELETE FROM library_anime WHERE id = :id")
    fun deleteAnime(id : Int) : Int

    /**
     * Updates the state of an anime item based on its ID.
     *
     * @param id The ID of the anime item to be updated.
     * @param newState The new state to be assigned to the anime item.
     * @return The number of rows affected.
     */
    @Query("UPDATE library_anime SET state = :newState WHERE id = :id")
    fun updateAnimeState(id : Int, newState : String) : Int


}