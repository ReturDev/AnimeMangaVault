package github.returdev.animemangavault.data.cache.dao

import androidx.paging.PagingSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import github.returdev.animemangavault.data.cache.model.entity.MangaCacheEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class MangaCacheDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("RoomTest")
    lateinit var mangaCacheDao: MangaCacheDao
    private val entitiesPage = createEntityList()


    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        mangaCacheDao.clearTable()
    }

    @Test
    fun shouldReturnAnEmptyListAfterInsertAndClearTheTable() = runBlocking {

        mangaCacheDao.insertPage(entitiesPage)
        mangaCacheDao.clearTable()


        val result = mangaCacheDao.getManga().load(PagingSource.LoadParams.Refresh(0, 2, false))
        assert(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page
        assert(pageResult.data.isEmpty())

    }

    @Test
    fun shouldReturnTheSameMangaEntityDataThatWasInserted() = runBlocking {

        mangaCacheDao.insertPage(entitiesPage)

        val result = mangaCacheDao.getManga().load(PagingSource.LoadParams.Refresh(0, 10, false))

        assert(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page
        for (i in pageResult.data.indices) {
            Assert.assertEquals(entitiesPage[i].title, pageResult.data[i].title)
        }
    }


    private fun createEntityList(): List<MangaCacheEntity> {

        val pageList = mutableListOf<MangaCacheEntity>()

        for (i in 0 until 5) {

            val entity = MangaCacheEntity(
                id = i,
                images = emptyList(),
                title = "Manga $i",
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
