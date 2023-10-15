package di

import features.menu.data.MenuRepository
import features.menu.data.source.MenuRepositoryImpl
import features.menu.data.source.local.LocalSource
import features.menu.data.source.local.LocalSourceImpl
import features.menu.data.source.local.entity.BurgerEntity
import features.menu.data.source.local.entity.IngredientEntity
import features.menu.data.source.remote.RemoteSource
import features.menu.data.source.remote.RemoteSourceImpl
import getHttpClient
import io.ktor.client.HttpClient
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val dataModule = module {
    single<HttpClient> {
        getHttpClient()
    }
    single<Realm> {
        Realm.open(
            RealmConfiguration.create(
                schema = setOf(
                    BurgerEntity::class,
                    IngredientEntity::class,
                )
            )
        )
    }
    single<LocalSource> { LocalSourceImpl(get()) }
    single<RemoteSource> { RemoteSourceImpl(get()) }
    single<CoroutineDispatcher> { Dispatchers.IO }
    single<MenuRepository> { MenuRepositoryImpl(get(), get(), get()) }
}
