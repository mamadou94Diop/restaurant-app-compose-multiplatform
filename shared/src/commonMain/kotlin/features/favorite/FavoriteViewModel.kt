package features.favorite

import features.common.UIState
import features.common.domain.BaseUseCase
import features.menu.domain.model.Burger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class FavoriteViewModel(private val getFavoriteBurgersUseCase: BaseUseCase<List<Burger>>) :
    ViewModel() {
    private val _stateFavorites =
        MutableStateFlow<UIState<List<Burger>>>(UIState.Loading)
    val stateFavorites: StateFlow<UIState<List<Burger>>> = _stateFavorites

    init {
        getFavorites()
    }
    fun getFavorites() {
        viewModelScope.launch {
            getFavoriteBurgersUseCase.execute()
                .collect { result ->
                    result.onSuccess { burgers ->
                        _stateFavorites.value = UIState.Success(burgers)
                    }
                        .onFailure {
                            _stateFavorites.value = UIState.Error("There an error on getting favorite ")
                        }
                }
        }
    }

    fun delete(burger: Burger) {

    }

}