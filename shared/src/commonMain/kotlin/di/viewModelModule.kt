package di

import features.cart.CartViewModel
import features.favorite.FavoriteViewModel
import features.menu.MenuViewModel
import features.order.OrderViewModel
import moe.tlaster.precompose.viewmodel.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    factory { MenuViewModel(get((named("menu"))), get()) }
    factory { FavoriteViewModel(get((named("favorite")))) }
    factory { OrderViewModel() }
}