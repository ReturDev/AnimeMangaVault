package github.returdev.animemangavault.data.library.dao

import androidx.paging.PagingSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import github.returdev.animemangavault.data.library.model.entity.AnimeLibraryEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date
import java.util.Random
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class AnimeLibraryDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("UserLibraryTest")
    lateinit var animeLibraryDao: AnimeLibraryDao

    private val animeLibraryList = createAnimeLibraryEntityList()

    @Before
    fun setUp() {
        hiltRule.inject()
        animeLibraryList.forEach(animeLibraryDao::insertAnime)
    }

    @After
    fun tearDown() {
        animeLibraryDao.clearAnimeTable()
    }


    @Test
    fun shouldReturnFavoriteAnimesSortedByDefaultTitleAscAndDesc() = runBlocking{
        val state = "FAVOURITES"
        val order = 2


        val animePagingSourceAsc = animeLibraryDao.getAnimesByState(state, order)
        val animePagingSourceDesc = animeLibraryDao.getAnimeByStateDesc(state, order)
        val animeLoadResultAsc = animePagingSourceAsc.load(PagingSource.LoadParams.Refresh(0, 5, false))
        val animeLoadResultDesc = animePagingSourceDesc.load(PagingSource.LoadParams.Refresh(0, 5, false))
        val animePageResultAsc = animeLoadResultAsc as PagingSource.LoadResult.Page
        val animePageResultDesc = animeLoadResultDesc as PagingSource.LoadResult.Page

        val animeListFiltered = animeLibraryList.filter { it.state == state}.sortedBy { it.defaultTitle }

        assertTrue(animePageResultAsc.data.all { it.state == state })
        assertTrue(animePageResultDesc.data.all { it.state == state })
        assertEquals(animeListFiltered.first(), animePageResultAsc.data.first())
        assertEquals(animeListFiltered.first(), animePageResultDesc.data.last())

    }

    @Test
    fun shouldReturnCorrectAnimeStateById() {

        val id = 5

        val animeGet = animeLibraryDao.getAnimeStateById(id)

        assertEquals(animeLibraryList[id].state, animeGet)

    }

    @Test
    fun shouldUpdateAnimeStateAndReturnItemCountAndNewState(){

        val id = 5
        val newState = "DROPPED"

        val animesUpdated = animeLibraryDao.updateAnimeState(id, newState)
        val animeUpdatedGet = animeLibraryDao.getAnimeStateById(id)

        assertEquals(1 , animesUpdated)
        assertEquals(animeUpdatedGet, newState)

    }

    private fun createAnimeLibraryEntityList(): List<AnimeLibraryEntity> {

        val list = mutableListOf<AnimeLibraryEntity>()
        val stateList = listOf("COMPLETED","ON_HOLD","FAVOURITES","COMPLETED","FAVOURITES")
        for (i in 0 until 10){
            val randomTitleNumber = Random().nextInt(100)
            val entity = AnimeLibraryEntity(
                id = i,
                imageUrl = "",
                defaultTitle = "Anime $randomTitleNumber $i",
                score = (i % 11).toFloat(),
                state = stateList[(i%5)] ,
                addedDate = Date(System.currentTimeMillis()),
            )
            list.add(entity)
        }

        return list

    }

}