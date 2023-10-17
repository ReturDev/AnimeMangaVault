package github.returdev.animemangavault.data.core.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlinx.coroutines.CoroutineDispatcher


@OptIn(ExperimentalPagingApi::class)
abstract class VisualMediaMediator<Value : Any>(
    val dispatcher : CoroutineDispatcher
) : RemoteMediator<Int, Value>(){

    companion object{
        private const val FIRST_PAGE_API = 1
    }

    // Indicates whether there is a next page available for loading.
    protected var hasNextPage = true
    // Keeps track of the current page number in the API.
    private var currentApiPage = FIRST_PAGE_API

    /**
     * Override function to implement the specific API data fetching and cache saving logic for visual media items.
     *
     * @param loadType The type of data load (refresh, prepend, append).
     * @param state The current state of the paging, containing information like the anchor position, items count, etc.
     * @return The `MediatorResult` indicating the result of the loading process.
     */
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Value>): MediatorResult {

        val mediatorResult : MediatorResult

        val apiPage = when(loadType){
            LoadType.REFRESH -> {
                currentApiPage
            }
            LoadType.PREPEND -> {
                null
            }
            LoadType.APPEND -> {
                ++currentApiPage
            }
        }

        mediatorResult = if (!hasNextPage || apiPage == null){

            MediatorResult.Success(true)

        }else{

            getApiDataAndSaveOnCache(apiPage, loadType)


        }

        return mediatorResult

    }

    /**
     * Abstract function to be implemented by subclasses.
     * This function is responsible for fetching data from the remote data source using `apiPage`
     * and saving the fetched data to the local cache database.
     *
     * @param apiPage The page number to fetch data from the remote data source.
     * @param loadType The type of data load (refresh, prepend, append).
     * @return The `MediatorResult` indicating the result of the loading process.
     */
    abstract suspend fun getApiDataAndSaveOnCache(apiPage : Int, loadType: LoadType) : MediatorResult

}