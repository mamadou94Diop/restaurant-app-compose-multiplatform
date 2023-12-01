package di

import features.cart.CartViewModel
import features.menu.MenuViewModel
import features.order.OrderViewModel
import moe.tlaster.precompose.viewmodel.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MenuViewModel(get(), get()) }
    factory { CartViewModel() }
    factory { OrderViewModel() }
}