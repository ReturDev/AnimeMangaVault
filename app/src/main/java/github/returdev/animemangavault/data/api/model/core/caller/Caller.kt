package github.returdev.animemangavault.data.api.model.core.caller

import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call

/**
 * Represents an interface for making asynchronous calls.
 */
interface Caller {

    /**
     * Executes a given [Call] and returns the result of the call.
     *
     * @param hasNetworkConnection Indicates whether there is an active network connection.
     * @param call The [Call] to execute.
     * @return The result of the call.
     */
    suspend fun <R> executeCall(hasNetworkConnection : StateFlow<Boolean>, call : Call<R>) : R

}