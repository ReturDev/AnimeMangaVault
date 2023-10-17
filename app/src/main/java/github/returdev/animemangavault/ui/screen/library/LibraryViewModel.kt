package github.returdev.animemangavault.ui.screen.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import github.returdev.animemangavault.core.extensions.toReducedAnimeUi
import github.returdev.animemangavault.core.extensions.toReducedMangaUi
import github.returdev.animemangavault.core.extensions.toUserLibraryOrderBy
import github.returdev.animemangavault.core.extensions.toUserLibrarySortDirection
import github.returdev.animemangavault.core.extensions.toUserLibraryVisualMediaStates
import github.returdev.animemangavault.domain.library.anime.GetAnimesFromUserLibraryUseCase
import github.returdev.animemangavault.domain.library.manga.GetMangaFromUserLibraryUseCase
import github.returdev.animemangavault.ui.model.filters.core.SortDirectionUi
import github.returdev.animemangavault.ui.model.filters.core.VisualMediaTypes
import github.returdev.animemangavault.ui.model.filters.library.UserLibraryOrderByUi
import github.returdev.animemangavault.ui.model.filters.library.UserLibraryVisualMediaStatesUi
import github.returdev.animemangavault.ui.model.reduced.ReducedVisualMediaUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getAnimesByState : GetAnimesFromUserLibraryUseCase,
    private val getMangasByState : GetMangaFromUserLibraryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState = _uiState.asStateFlow()

    val currentVMItems : Flow<PagingData<out ReducedVisualMediaUi>> =

        _uiState.flatMapLatest { currentState ->

            when(currentState.vmTypeSelected){
                VisualMediaTypes.ANIME -> {
                    getAnimesByState(
                        state = currentState.animeStateSelected.toUserLibraryVisualMediaStates(),
                        sortDirection = currentState.currentSortDirection.toUserLibrarySortDirection(),
                        orderBy = currentState.currentOrderBy.toUserLibraryOrderBy()
                    ).map { pagingData ->
                        pagingData.map { it.toReducedAnimeUi() }
                    }.cachedIn(viewModelScope)
                }
                VisualMediaTypes.MANGA -> {
                    getMangasByState(
                        state = currentState.mangaStateSelected.toUserLibraryVisualMediaStates(),
                        sortDirection = currentState.currentSortDirection.toUserLibrarySortDirection(),
                        orderBy = currentState.currentOrderBy.toUserLibraryOrderBy()
                    ).map { pagingData ->
                        pagingData.map { it.toReducedMangaUi() }
                    }.cachedIn(viewModelScope)
                }

            }

        }

    val changeAnimeStateSelected : (UserLibraryVisualMediaStatesUi) -> Unit = { state ->

        _uiState.update {currentState ->

            currentState.copy(
                animeStateSelected = state
            )

        }

    }

    val changeMangaStateSelected : (UserLibraryVisualMediaStatesUi) -> Unit = { state ->

        _uiState.update {currentState ->

            currentState.copy(
                mangaStateSelected = state
            )

        }

    }

    val changeSortDirection : () -> Unit = {

        _uiState.update {currentState ->

            currentState.copy(
                currentSortDirection = when(currentState.currentSortDirection){
                    SortDirectionUi.ASCENDANT -> SortDirectionUi.DESCENDANT
                    SortDirectionUi.DESCENDANT -> SortDirectionUi.ASCENDANT
                }
            )
        }

    }

    val changeVMType : (VisualMediaTypes) -> Unit = { currentType ->

        _uiState.update {currentState ->

            currentState.copy(
                vmTypeSelected = currentType
            )

        }

    }
    val changeOrderBy : (UserLibraryOrderByUi) -> Unit = { currentOrderBy ->

        _uiState.update {currentState ->

            currentState.copy(
                currentOrderBy = currentOrderBy
            )

        }

    }



}

