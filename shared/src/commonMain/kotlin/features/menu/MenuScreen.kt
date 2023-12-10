package features.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberImagePainter
import features.common.UIState
import features.common.ui.ErrorView
import features.common.ui.LoadingView
import features.menu.domain.model.Burger
import features.menu.domain.model.Menu

@Composable
fun MenuScreen(viewModel: MenuViewModel, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier.padding(paddingValues).fillMaxSize()
    ) {
        val state = viewModel.stateMenu.collectAsState()

        when (state.value) {
            is UIState.Loading -> LoadingView()
            is UIState.Error -> ErrorView(
                message = (state.value as UIState.Error<Menu>).message,
                retryMessage = "Retry",
                onRetry = {
                    viewModel.getBurgers()
                })

            is UIState.Success -> (state.value as UIState.Success<Menu>).data.apply {
                BurgerListView(
                    burgers = burgers,
                    favorites = favorites,
                    toggleFavorite = { burger ->
                        viewModel.toggleFavorite(burger)
                    }
                )
            }
        }

    }
}

@Composable
fun BurgerListView(burgers: List<Burger>, favorites: List<Int>, toggleFavorite: (Burger) -> Unit) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(burgers) { burger ->
            BurgerItem(burger, favorites.contains(burger.id), toggleFavorite)
        }
    }
}

@Composable
fun BurgerItem(burger: Burger, isFavorite: Boolean, toggleFavorite: (Burger) -> Unit) {
    val painter = rememberImagePainter(burger.image)
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var isDetailVisible: Boolean by rememberSaveable { mutableStateOf(false) }
            Image(
                painter = painter,
                modifier = Modifier.fillMaxSize().height(256.dp),
                contentDescription = null,
            )
            Spacer(Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    burger.name,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                      toggleFavorite.invoke(burger)
                    },
                    content = {
                        if (isFavorite) {
                            Icon(Icons.Filled.Favorite, contentDescription = null)

                        } else {
                            Icon(Icons.Filled.FavoriteBorder, contentDescription = null)
                        }
                    })
            }
            Text(
                "${burger.price} $",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(Modifier.height(8.dp))
            Button(onClick = {}) {
                Text("Add to cart")
            }

            Spacer(Modifier.height(8.dp))
            IconButton(
                onClick = {
                    isDetailVisible = !isDetailVisible
                },
                content = {
                    if (isDetailVisible) {
                        Icon(Icons.Filled.ExpandLess, contentDescription = null)
                    } else {
                        Icon(Icons.Filled.ExpandMore, contentDescription = null)
                    }
                })

            AnimatedVisibility(visible = isDetailVisible) {
                Spacer(Modifier.height(4.dp))
                Text(
                    burger.description,
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(4.dp))
            }

        }
    }
}

