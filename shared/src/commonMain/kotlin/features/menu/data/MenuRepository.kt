package features.menu.data

import features.menu.domain.model.Burger
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getBurgers(): Flow<Result<List<Burger>>>
}