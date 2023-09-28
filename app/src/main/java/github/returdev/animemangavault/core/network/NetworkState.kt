package github.returdev.animemangavault.core.network

/**
 * Sealed class representing the network status states.
 */
sealed class NetworkState {
    object Lost : NetworkState()
    object Available : NetworkState()
}
