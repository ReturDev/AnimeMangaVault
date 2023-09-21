package github.returdev.animemangavault.data.api.model.core.caller


import github.returdev.animemangavault.core.exceptions.ApiExceptions.*
import github.returdev.animemangavault.core.exceptions.NetworkException
import github.returdev.animemangavault.data.api.model.core.ApiResponseCode
import kotlinx.coroutines.delay
import retrofit2.Call
import javax.inject.Inject

/**
 * An implementation of the [Caller] interface for making API calls related to anime and manga.
 * This implementation handles retries with specific parameters.
 *
 * @property maxRetries The maximum number of retries allowed for a call.
 * @property initialDelayMillis The initial delay, in milliseconds, before the first retry.
 * @property maxDelayMillis The maximum delay, in milliseconds, between retries.
 * @property repeatTimeMultiplier The multiplier used to calculate the delay between retries.
 */
class AnimeMangaApiCaller @Inject constructor() : Caller {
    val maxRetries: Int = 5
    val initialDelayMillis: Long = 1000
    val maxDelayMillis: Long = 3000
    val repeatTimeMultiplier: Float = 2f

    var retryCount : Int = 0
        private set
    var currentDelay = initialDelayMillis
        private set

    /**
     * Executes a given [Call] and returns the result of the call, with support for retries.
     *
     * @param hasNetworkConnection Indicates whether there is an active network connection.
     *                            If `false`, a [NetworkException] is thrown, and the call is not executed.
     * @param call The [Call] to execute.
     * @return The result of the call.
     * @throws NetworkException if [hasNetworkConnection] is `false`.
     * @throws RateLimitException if the API responds with a "TOO_MANY_REQUEST" error after exhausting retries.
     * @throws ServerInternalException if the API responds with an "INTERNAL_SERVER_ERROR".
     * @throws RuntimeException for unexpected API response codes.
     */
    override suspend fun <R> executeCall(hasNetworkConnection : Boolean, call: Call<R>): R {

        resetCaller()

        if (!hasNetworkConnection){
            throw NetworkException()
        }

        var result : R? = null

        while (retryCount <= maxRetries){

            val callResponse = call.execute()

            if (callResponse.isSuccessful){

                result =  callResponse.body()!!
                break

            }else{

                when (callResponse.code()) {
                    ApiResponseCode.TOO_MANY_REQUEST -> {
                        if (retryCount == maxRetries) {throw RateLimitException}
                    }
                    ApiResponseCode.INTERNAL_SERVER_ERROR -> throw ServerInternalException
                    else -> throw RuntimeException()
                }

            }

            retryCount++

            delay(currentDelay)

            incrementDelay()

        }

        return result!!

    }

    /**
     * Increments the delay between retries based on the [repeatTimeMultiplier] and caps it at [maxDelayMillis].
     */
    private fun incrementDelay(){
        currentDelay = if (currentDelay * repeatTimeMultiplier > maxDelayMillis){
            maxDelayMillis
        }else{
            (currentDelay * repeatTimeMultiplier).toLong()
        }
    }

    /**
     * Resets the retry count and current delay to their initial values.
     */
    private fun resetCaller() {
        retryCount = 0
        currentDelay = initialDelayMillis
    }

}