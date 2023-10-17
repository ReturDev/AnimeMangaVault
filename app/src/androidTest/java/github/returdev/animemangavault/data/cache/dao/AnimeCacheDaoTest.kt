package github.returdev.animemangavault.data.cache.dao

import androidx.paging.PagingSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import github.returdev.animemangavault.data.cache.model.entity.AnimeCacheEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class AnimeCacheDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("RoomTest")
    lateinit var animeCacheDao: AnimeCacheDao
    private val entitiesPage = createEntityList()


    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        animeCacheDao.clearTable()
    }

    @Test
    fun shouldReturnAnEmptyListAfterInsertAndClearTheTable() = runBlocking{

        animeCacheDao.insertPage(entitiesPage)
        animeCacheDao.clearTable()


        val result = animeCacheDao.getAnime().load(PagingSource.LoadParams.Refresh(0,2,false))
        assert(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page
        assert(pageResult.data.isEmpty())

    }

    @Test
    fun shouldReturnTheSameAnimeEntityDataThatWasInserted () = runBlocking{

        animeCacheDao.insertPage(entitiesPage)

        val result = animeCacheDao.getAnime().load(PagingSource.LoadParams.Refresh(0,5,false))

        assert(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page
        for (i in pageResult.data.indices){
            Assert.assertEquals(entitiesPage[i].title, pageResult.data[i].title)
        }
    }

    private fun createEntityList() : List<AnimeCacheEntity>{
        val pageList = mutableListOf<AnimeCacheEntity>()
        for (i in 0 until 5){

            val entity = AnimeCacheEntity(
                id = i,
                images = emptyList(),
                title = "Anime $i",
                score = i % 11f,
                type = "",
                genres = listOf(),
                demographics = listOf()
            )

            pageList.add(entity)

        }

        return pageList
    }

}