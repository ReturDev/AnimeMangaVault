package github.returdev.animemangavault.data.api.core.caller

import github.returdev.animemangavault.core.network.NetworkState
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call

/**
 * Represents an interface for making asynchronous calls.
 */
interface Caller {

    /**
     * Executes a given [Call] and returns the result of the call.
     *
     * @param networkState Indicates whether there is an active network connection.
     * @param call The [Call] to execute.
     * @return The result of the call.
     */
    suspend fun <R> executeCall(networkState : StateFlow<NetworkState>, call : Call<R>) : R

}