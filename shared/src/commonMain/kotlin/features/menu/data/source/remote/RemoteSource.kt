package features.menu.data.source.remote

import features.menu.data.source.remote.dto.BurgerDto

interface RemoteSource {
    suspend fun  getBurgers(): List<BurgerDto>
}