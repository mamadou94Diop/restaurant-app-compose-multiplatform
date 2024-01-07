package features.menu

import features.common.UIState
import features.common.domain.BaseUseCase
import features.favorite.data.FavoriteRepository
import features.menu.domain.model.Burger
import features.menu.domain.model.Menu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class MenuViewModel(
    private val useCase: BaseUseCase<Menu>,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    private val _stateMenu =
        MutableStateFlow<UIState<Menu>>(UIState.Loading)
    val stateMenu: StateFlow<UIState<Menu>> = _stateMenu

    init {
        getBurgers()
    }

    fun getBurgers() {
        viewModelScope.launch {
            _stateMenu.value = UIState.Loading
            useCase.execute()
                .collect { result ->
                    result
                        .onSuccess {
                            _stateMenu.value = UIState.Success(
                                result.getOrDefault(
                                    Menu(
                                        burgers = listOf(),
                                        favorites = listOf()
                                    )
                                )
                            )
                        }
                        .onFailure {
                            _stateMenu.value =
                                UIState.Error(result.exceptionOrNull()?.message.orEmpty())
                        }
                }
        }
    }


    fun toggleFavorite(burger: Burger) {
        viewModelScope.launch {
            favoriteRepository.setFavorite(burger)
                .collect { isToggleSuccessful ->
                    if (isToggleSuccessful) {
                        _stateMenu.apply {
                            val currentState = this.value as UIState.Success

                            val (_, favorites) = currentState.data

                            val updatedFavorites = if (favorites.contains(burger.id)) {
                                favorites.minus(burger.id)
                            } else {
                                favorites.plus(burger.id)
                            }

                            value = currentState.copy(
                                data = currentState.data.copy(favorites = updatedFavorites)
                            )
                        }
                    }
                }
        }
    }

}