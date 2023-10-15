package features.common

sealed class UIState<out T> {
    data object Loading : UIState<Nothing>()
    data class Error<T>(val message: String) : UIState<T>()
    data class Success<T>(val data: T) : UIState<T>()

}