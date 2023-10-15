package features.menu.data.source.local

import features.menu.data.source.local.entity.BurgerEntity
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    fun getBurgers(): Flow<List<BurgerEntity>>
    fun insertBurgers(Burgers: List<BurgerEntity>)
    fun setFavorite(Burger: BurgerEntity)
    fun deleteAllBurgers()
}