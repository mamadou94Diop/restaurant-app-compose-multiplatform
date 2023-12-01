package features.menu.domain.usecase

import features.common.domain.BaseUseCase
import features.favorite.data.FavoriteRepository
import features.menu.data.MenuRepository
import features.menu.domain.model.Menu
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetMenuUseCase(
    private val repository: MenuRepository,
    private val favoriteRepository: FavoriteRepository
) : BaseUseCase<Menu> {
    override suspend fun execute(): Flow<Result<Menu>> {
        return flow {
            repository.getBurgers().combine(
                favoriteRepository.getFavoriteBurgers()
            ) { result, favorites -> result to favorites }
                .collect { (burgersResult, favorites) ->
                    burgersResult
                        .onSuccess {
                            emit(
                                Result.success(
                                    Menu(
                                        burgers = burgersResult.getOrDefault(emptyList()),
                                        favorites = favorites.map { it.id })
                                )
                            )
                        }
                        .onFailure {
                            emit(Result.failure(burgersResult.exceptionOrNull() ?: Exception()))
                        }
                }
        }

    }
}