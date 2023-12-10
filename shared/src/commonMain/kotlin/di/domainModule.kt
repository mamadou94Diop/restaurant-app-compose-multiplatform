package di

import features.common.domain.BaseUseCase
import features.favorite.domain.GetFavoriteBurgersUseCase
import features.menu.domain.model.Burger
import features.menu.domain.model.Menu
import features.menu.domain.usecase.GetMenuUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainModule = module {
    single(named("menu")) { GetMenuUseCase(get(), get()) as BaseUseCase<Menu>}
    single(named("favorite")) { GetFavoriteBurgersUseCase(get()) as BaseUseCase<List<Burger>> }
}