package features.menu.data.source

import features.menu.data.MenuRepository
import features.menu.data.source.local.LocalSource
import features.menu.data.source.remote.RemoteSource
import features.menu.data.source.remote.dto.BurgerDto
import features.menu.domain.model.Burger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse

class MenuRepositoryImpl(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource,
    private val dispatcher: CoroutineDispatcher
) : MenuRepository {

    private val storeBurger =
        StoreBuilder.from(
            fetcher = Fetcher.of {
                remoteSource.getBurgers()
            },
            sourceOfTruth = SourceOfTruth.Companion.of(
                reader = { _: String ->
                    flow<List<Burger>> {
                        localSource.getBurgers().collect { burgers ->
                            println(burgers)
                            emit(burgers.map { it.toDomain() })
                        }
                    }
                },
                writer = { _, burgersDto: List<BurgerDto> ->
                    localSource.deleteAllBurgers()
                    localSource.insertBurgers(burgersDto.map {
                        it.toDomain().toEntity()
                    })
                },
            )
        ).build()


    override fun getBurgers(): Flow<Result<List<Burger>>> =
        flow<Result<List<Burger>>> {
            storeBurger.stream(StoreReadRequest.cached(key = "burgers", refresh = false))
                .collect {
                    when (it) {
                        is StoreReadResponse.Data -> {
                            emit(Result.success(it.value))
                        }
                        is StoreReadResponse.Error.Exception -> emit(Result.failure(it.error))
                        is StoreReadResponse.Error.Message -> emit(Result.failure(Exception(it.message)))
                        is StoreReadResponse.NoNewData -> emit(Result.success(emptyList()))
                        is StoreReadResponse.Loading -> {

                        }
                    }

                }
        }.flowOn(dispatcher)
}