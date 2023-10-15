package navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import features.cart.CartScreen
import features.cart.CartViewModel
import features.favorite.FavoriteScreen
import features.menu.MenuScreen
import features.menu.MenuViewModel
import features.order.OrderScreen
import features.order.OrderViewModel
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold() {
    val navigator = rememberNavigator()
    Scaffold(
        bottomBar = {
            BottomNav(navigator)
        },
        content = {
            NavContent(navigator, it)
        }
    )

}

@Composable
fun BottomNav(navigator: Navigator) {
    NavigationBar {
        val currentEntry by navigator.currentEntry.collectAsState(null)
        val path = currentEntry?.path
        navigationItems.forEach { navigationItem ->
            NavigationBarItem(
                icon = { Icon(navigationItem.icon, contentDescription = null) },
                label = { Text(navigationItem.name) },
                selected = path == navigationItem.route,
                onClick = {
                    navigator.navigate(navigationItem.route)
                }
            )
        }

    }
}

@Composable
fun NavContent(navigator: Navigator, paddingValues: PaddingValues) {
    NavHost(
        initialRoute = NavigationItem.Menu.route,
        navigator = navigator,
        navTransition = NavTransition()
    ) {
        scene(NavigationItem.Menu.route) {
            val viewModel = koinViewModel(MenuViewModel::class)
            MenuScreen(viewModel, paddingValues)

        }

        scene(NavigationItem.Cart.route) {
            val viewModel = koinViewModel(CartViewModel::class)
            CartScreen(viewModel, paddingValues)
        }

        scene(NavigationItem.Orders.route) {
            val viewModel = koinViewModel(OrderViewModel::class)
            OrderScreen(viewModel, paddingValues)
        }

        scene(NavigationItem.Favorite.route) {
            //val viewModel = koinViewModel(CartViewModel::class)
            FavoriteScreen(paddingValues)
        }
    }
}

