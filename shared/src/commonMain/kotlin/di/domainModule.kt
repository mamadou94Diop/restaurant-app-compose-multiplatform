package di

import features.common.domain.BaseUseCase
import features.menu.domain.model.Burger
import features.menu.domain.model.Menu
import features.menu.domain.usecase.GetMenuUseCase
import org.koin.dsl.module

val domainModule = module {
    single<BaseUseCase<Menu>> { GetMenuUseCase(get(), get()) }
}