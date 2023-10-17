package github.returdev.animemangavault.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Singleton class responsible for monitoring network connectivity changes.
 *
 * @param context The application context.
 */
@Singleton
class NetworkConnectivity @Inject constructor(
    @ApplicationContext private val context : Context
) {

    private val connectivityManager = context.getSystemService(ConnectivityManager::class.java)

    // A mutable state flow to represent the current network state.
    private val _networkState = MutableStateFlow(checkStartConnection())
    val networkState = _networkState.asStateFlow()


    /**
     * Initializes the network connectivity monitoring and callback.
     */
    init {
        initConnectivityCallback()
    }


    /**
     * Initializes the network callback to monitor network changes.
     */
    private fun initConnectivityCallback() {
        val connectivityCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                // Network capabilities have changed, indicating available network.
                _networkState.value = NetworkState.Available
            }

            override fun onLost(network: Network) {
                // Network connection has been lost.
                _networkState.value = NetworkState.Lost
            }

        }

        val networkRequest = NetworkRequest.Builder().addCapability(
            NetworkCapabilities.NET_CAPABILITY_VALIDATED
        ).build()

        // Register the network callback to start monitoring.
        connectivityManager.registerNetworkCallback(networkRequest, connectivityCallback)

    }

    /**
     * Checks the initial network connection state.
     *
     * @return The initial network state.
     */
    private fun checkStartConnection(): NetworkState {
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return networkCapabilities?.let { NetworkState.Available } ?: NetworkState.Lost

    }

}