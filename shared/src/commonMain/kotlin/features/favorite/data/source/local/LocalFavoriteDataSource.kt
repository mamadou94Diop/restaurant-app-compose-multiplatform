package features.favorite.data.source.local

import features.favorite.data.source.FavoriteDataSource
import features.favorite.data.source.local.entity.FavoriteBurgerEntity
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class LocalFavoriteDataSource(private val realm: Realm) : FavoriteDataSource {
    override fun getFavoriteBurgers(): Flow<List<FavoriteBurgerEntity>> =
        realm.query(FavoriteBurgerEntity::class).asFlow().map {
            it.list.toList()

        }

    override fun setFavorite(favorite: FavoriteBurgerEntity) = flow {
        try {
            realm.query<FavoriteBurgerEntity>(FavoriteBurgerEntity::class, "id == $0", favorite.id)
                .first()
                .find()
                ?.let { burgerToRemove ->
                    realm.writeBlocking {
                        findLatest(burgerToRemove)
                            ?.also {
                                delete(it)
                            }
                    }
                } ?: kotlin.run {
                realm.writeBlocking {
                    copyToRealm(favorite)
                }
            }
            emit(true)
        } catch (exception: Exception) {
            emit(false)
        }
    }
}