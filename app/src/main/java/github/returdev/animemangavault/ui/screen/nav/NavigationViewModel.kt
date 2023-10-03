package github.returdev.animemangavault.ui.screen.nav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import github.returdev.animemangavault.core.network.NetworkConnectivity
import github.returdev.animemangavault.core.network.NetworkState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    networkConnectivity: NetworkConnectivity
) : ViewModel() {

    val uiState : StateFlow<NavigationUiState> = networkConnectivity.networkState.map { networkState ->
        NavigationUiState(networkState is NetworkState.Lost)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NavigationUiState()
    )


}