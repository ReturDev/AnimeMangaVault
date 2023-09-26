package github.returdev.animemangavault.data.api.model.core.caller

import github.returdev.animemangavault.core.exceptions.ApiExceptions
import github.returdev.animemangavault.core.exceptions.NetworkException
import github.returdev.animemangavault.core.network.NetworkState
import github.returdev.animemangavault.data.api.core.ApiResponseCode
import github.returdev.animemangavault.data.api.core.caller.AnimeMangaApiCaller
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response

class AnimeMangaApiCallerTest {

    private lateinit var caller : AnimeMangaApiCaller

    @Mock
    private lateinit var call : Call<Any>
    @Mock
    private lateinit var fakeResponse : Response<Any>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        caller = AnimeMangaApiCaller()
        Mockito.`when`(call.execute()).thenReturn(fakeResponse)
    }

    @Test
    fun `should return a response in the first try`() = runBlocking{

        val body = Any()
        val networkConnection = MutableStateFlow(NetworkState.Available)

        Mockito.`when`(fakeResponse.isSuccessful).thenReturn(true)
        Mockito.`when`(fakeResponse.body()).thenReturn(body)

        val result = caller.executeCall(networkConnection, call)

        Mockito.verify(call, Mockito.only()).execute()
        assertEquals(body, result)

    }

    @Test
    fun `should throw a ServerInternalException`() = runBlocking{

        val networkConnection = MutableStateFlow(NetworkState.Available)

        Mockito.`when`(fakeResponse.isSuccessful).thenReturn(false)
        Mockito.`when`(fakeResponse.code()).thenReturn(ApiResponseCode.INTERNAL_SERVER_ERROR)

        try {
            caller.executeCall(networkConnection, call)
            fail("Expected ServerInternalException was not thrown")
        }catch ( _ : ApiExceptions.ServerInternalException){}

    }

    @Test
    fun `should return the result after 1 TOO_MANY_REQUEST error codes `() = runBlocking{
        val body = Any()
        val networkConnection = MutableStateFlow(NetworkState.Available)

        Mockito.`when`(fakeResponse.isSuccessful)
            .thenReturn(false)
            .thenReturn(true)
        Mockito.`when`(fakeResponse.code()).thenReturn(ApiResponseCode.TOO_MANY_REQUEST)
        Mockito.`when`(fakeResponse.body()).thenReturn(body)


        val result = caller.executeCall(networkConnection,call)

        Mockito.verify(call, Mockito.times(2)).execute()
        assertEquals(body, result)

    }


    @Test
    fun `should throw a RateLimitException`() = runBlocking{

        val networkConnection = MutableStateFlow(NetworkState.Available)

        Mockito.`when`(fakeResponse.isSuccessful).thenReturn(false)
        Mockito.`when`(fakeResponse.code()).thenReturn(ApiResponseCode.TOO_MANY_REQUEST)

       try {
           caller.executeCall(networkConnection,call)
           fail("Expected RateLimitException was not thrown")
       }catch (_ : ApiExceptions.RateLimitException){}

    }

    @Test
    fun `should throw a RunTimeException`() = runBlocking{
        val networkConnection = MutableStateFlow(NetworkState.Available)

        Mockito.`when`(fakeResponse.isSuccessful).thenReturn(false)

        try {
            caller.executeCall(networkConnection,call)
            fail("Expected RunTimeException was not thrown")
        }catch (_ : RuntimeException){}

    }

    @Test
    fun `should throw a NetworkException`() = runBlocking{
        val networkConnection = MutableStateFlow(NetworkState.Lost)

        try {
            caller.executeCall(networkConnection,call)
            fail()
        }catch (_ : NetworkException){}

    }


    @Test
    fun `should throw a NetworkException after 2 tries by rate limit code`() = runBlocking {

        val networkConnection = MutableStateFlow<NetworkState>(NetworkState.Available)

        Mockito.`when`(fakeResponse.isSuccessful).thenReturn(false)
        Mockito.`when`(fakeResponse.code()).thenReturn(ApiResponseCode.TOO_MANY_REQUEST)

        try {

            launch {
                delay((caller.initialDelayMillis * 2.5).toLong())
                networkConnection.value = NetworkState.Lost
            }
            caller.executeCall(networkConnection,call)
            fail("Expected NetworkException was not thrown")

        }catch (_ : NetworkException){
            Mockito.verify(call, Mockito.times(2)).execute()
            assert(true)
        }

    }

}