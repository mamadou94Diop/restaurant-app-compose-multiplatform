package features.menu.data.source.remote

import config.API_BASE_URL
import features.menu.data.source.remote.dto.BurgerDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RemoteSourceImpl(private val client: HttpClient) : RemoteSource {
    override suspend fun getBurgers(): List<BurgerDto> {
        return client.get("$API_BASE_URL/burgers").body()
    }


}