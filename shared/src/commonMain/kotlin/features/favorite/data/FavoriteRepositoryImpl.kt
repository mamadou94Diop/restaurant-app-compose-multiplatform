package features.favorite.data

import features.favorite.data.source.FavoriteDataSource
import features.favorite.data.source.local.entity.FavoriteBurgerEntity
import features.menu.data.source.toEntity
import features.menu.domain.model.Burger
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.Flow

class FavoriteRepositoryImpl(
    private val localSource: FavoriteDataSource,
) : FavoriteRepository {
    override fun setFavorite(burger: Burger): Flow<Boolean> = localSource.setFavorite(FavoriteBurgerEntity().apply {
        id = burger.id
        name = burger.name
        image = burger.image
        description = burger.description
        price = burger.price
        ingredients = burger.toEntity().ingredients
    })
    override fun getFavoriteBurgers(): Flow<List<FavoriteBurgerEntity>> =
        localSource.getFavoriteBurgers()
}