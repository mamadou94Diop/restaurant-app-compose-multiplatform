package di

import features.cart.CartViewModel
import features.menu.MenuViewModel
import features.order.OrderViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MenuViewModel(get()) }
    factory { CartViewModel() }
    factory { OrderViewModel() }
}