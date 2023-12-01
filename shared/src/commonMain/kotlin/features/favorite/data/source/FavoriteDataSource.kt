package features.favorite.data.source

import features.favorite.data.source.local.entity.FavoriteBurgerEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteDataSource {
    fun  getFavoriteBurgers() : Flow<List<FavoriteBurgerEntity>>
    fun setFavorite(favorite: FavoriteBurgerEntity): Flow<Boolean>

}