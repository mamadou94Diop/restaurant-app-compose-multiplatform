package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(val route: String, val name: String, val icon: ImageVector) {
    data object Menu :
        NavigationItem(route = "/menu", name = "Menu", icon = Icons.Filled.RestaurantMenu)

    data object Cart :
        NavigationItem(route = "/cart", name = "Cart", icon = Icons.Filled.ShoppingCart)

    data object Orders :
        NavigationItem(route = "/orders", name = "Orders", icon = Icons.Filled.Receipt)

    data object Favorite :
        NavigationItem(route = "/favorite", name = "Favorite", icon = Icons.Filled.Favorite)
}

val navigationItems =
    listOf(NavigationItem.Menu, NavigationItem.Cart, NavigationItem.Orders, NavigationItem.Favorite)