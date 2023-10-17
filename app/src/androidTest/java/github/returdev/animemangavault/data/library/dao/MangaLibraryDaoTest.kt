package github.returdev.animemangavault.data.library.dao

import androidx.paging.PagingSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import github.returdev.animemangavault.data.library.model.entity.MangaLibraryEntity
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
class MangaLibraryDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("UserLibraryTest")
    lateinit var mangaLibraryDao: MangaLibraryDao

    private val mangaLibraryList = createMangaLibraryEntityList()

    @Before
    fun setUp() {
        hiltRule.inject()
        mangaLibraryList.forEach(mangaLibraryDao::insertManga)
    }

    @After
    fun tearDown() {
        mangaLibraryDao.clearMangaTable()
    }


    @Test
    fun shouldReturnOnHoldAnimesSortedByScoreAscAndDesc() = runBlocking{
        val state = "ON_HOLD"
        val order = 3


        val animePagingSourceAsc = mangaLibraryDao.getMangasByState(state, order)
        val animePagingSourceDesc = mangaLibraryDao.getMangasByStateDesc(state, order)
        val animeLoadResultAsc = animePagingSourceAsc.load(PagingSource.LoadParams.Refresh(0, 5, false))
        val animeLoadResultDesc = animePagingSourceDesc.load(PagingSource.LoadParams.Refresh(0, 5, false))
        val animePageResultAsc = animeLoadResultAsc as PagingSource.LoadResult.Page
        val animePageResultDesc = animeLoadResultDesc as PagingSource.LoadResult.Page

        val animeListFiltered = mangaLibraryList.filter { it.state == state}.sortedBy { it.score }

        assertTrue(animePageResultAsc.data.all { it.state == state })
        assertTrue(animePageResultDesc.data.all { it.state == state })
        assertEquals(animeListFiltered.first(), animePageResultAsc.data.first())
        assertEquals(animeListFiltered.first(), animePageResultDesc.data.last())

    }

    @Test
    fun shouldReturnCorrectAnimeStateById() {

        val id = 5

        val animeGet = mangaLibraryDao.getMangaStateById(id)

        assertEquals(mangaLibraryList[id].state, animeGet)

    }

    @Test
    fun shouldUpdateAnimeStateAndReturnItemCountAndNewState(){

        val id = 5
        val newState = "DROPPED"

        val animesUpdated = mangaLibraryDao.updateMangaState(id, newState)
        val animeUpdatedGet = mangaLibraryDao.getMangaStateById(id)

        assertEquals(1 , animesUpdated)
        assertEquals(animeUpdatedGet, newState)

    }

    private fun createMangaLibraryEntityList(): List<MangaLibraryEntity> {

        val list = mutableListOf<MangaLibraryEntity>()
        val stateList = listOf("COMPLETED","ON_HOLD","FAVOURITES","COMPLETED","FAVOURITES")
        for (i in 0 until 10){
            val randomTitleNumber = Random().nextInt(100)
            val entity = MangaLibraryEntity(
                id = i,
                imageUrl = "",
                defaultTitle = "Manga $randomTitleNumber $i",
                score = (i % 11).toFloat(),
                state = stateList[(i%5)] ,
                addedDate = Date(System.currentTimeMillis()),
            )
            list.add(entity)
        }

        return list

    }
}