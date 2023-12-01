package features.favorite.data

import features.favorite.data.source.local.entity.FavoriteBurgerEntity
import features.menu.domain.model.Burger
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun setFavorite(burger: Burger) : Flow<Boolean>
    fun getFavoriteBurgers() : Flow<List<FavoriteBurgerEntity>>

}