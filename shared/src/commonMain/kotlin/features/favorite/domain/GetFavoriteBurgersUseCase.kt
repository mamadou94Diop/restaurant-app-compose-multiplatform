package features.favorite.domain

import features.common.domain.BaseUseCase
import features.favorite.data.FavoriteRepository
import features.menu.domain.model.Burger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import toBurger

class GetFavoriteBurgersUseCase(private val favoriteRepository: FavoriteRepository) :
    BaseUseCase<List<Burger>> {
    override suspend fun execute(): Flow<Result<List<Burger>>> {
        return favoriteRepository.getFavoriteBurgers()
            .map { favorites ->
                Result.success(favorites.map { it.toBurger() })
            }
    }
}