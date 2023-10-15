package features.menu

import features.common.UIState
import features.menu.data.MenuRepository
import features.menu.domain.model.Burger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class MenuViewModel(private val repository: MenuRepository) : ViewModel() {
    private val _statePizzas =
        MutableStateFlow<UIState<List<Burger>>>(UIState.Loading)
    val statePizzas: StateFlow<UIState<List<Burger>>> = _statePizzas


    init {
        getBurgers()
    }

    fun getBurgers() {
        viewModelScope.launch {
            _statePizzas.value = UIState.Loading
            repository.getBurgers()
                .collect { result ->
                    if (result.isFailure) {
                        _statePizzas.value =
                            UIState.Error(result.exceptionOrNull()?.message.orEmpty())
                    } else {
                        _statePizzas.value = UIState.Success(result.getOrDefault(emptyList()))
                    }
                }
        }
    }

    fun addToCart(burger: Burger) {

    }

    fun removeFromCart(burger: Burger) {

    }

    fun toggleFavorite(burger: Burger) {

    }

}