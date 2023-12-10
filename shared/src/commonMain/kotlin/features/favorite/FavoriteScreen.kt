package features.favorite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
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


@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier.padding(paddingValues).fillMaxSize()
    ) {
        val state = viewModel.stateFavorites.collectAsState()

        when (state.value) {
            is UIState.Loading -> LoadingView()
            is UIState.Error -> ErrorView(
                message = (state.value as UIState.Error<List<Burger>>).message,
                retryMessage = "Retry",
                onRetry = {
                    viewModel.getFavorites()
                })

            is UIState.Success -> (state.value as UIState.Success<List<Burger>>).data.apply {
                FavoriteListView(
                    burgers = this,
                    onDelete = { burger ->
                        viewModel.delete(burger)
                    }
                )
            }
        }

    }
}

@Composable
fun FavoriteListView(burgers: List<Burger>, onDelete: (Burger) -> Unit) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(burgers) { burger ->
            FavoriteItem(burger, onDelete)
        }
    }
}

@Composable
fun FavoriteItem(burger: Burger, onDelete: (Burger) -> Unit) {
    println("territoire ${burger}")
    val painter = rememberImagePainter(burger.image)
    var isDetailVisible: Boolean by rememberSaveable { mutableStateOf(false) }
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp, vertical = 8.dp)) {
       Column {
           Row(
               modifier = Modifier.padding(vertical = 8.dp),
               verticalAlignment = Alignment.Top
           ) {

               Image(
                   painter = painter,
                   modifier = Modifier.width(128.dp)
                       .height(128.dp),
                   contentDescription = null,
               )
               Spacer(Modifier.height(4.dp))
               Column {
                   Text(
                       burger.name,
                       textAlign = TextAlign.Center,
                       fontWeight = FontWeight.Bold
                   )
                   Spacer(Modifier.height(2.dp))

                   Text(
                       "${burger.price} $",
                       textAlign = TextAlign.Center,
                       fontWeight = FontWeight.Bold,
                       fontSize = 24.sp
                   )
               }
               IconButton(
                   onClick = {
                       onDelete.invoke(burger)
                   },
                   content = {
                       Icon(Icons.Filled.Delete, contentDescription = null)
                   })
           }
           IconButton(modifier = Modifier.align(Alignment.CenterHorizontally),
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
               Text(
                   burger.description,
                   textAlign = TextAlign.Justify,
                   fontWeight = FontWeight.W400,
               )
           }
       }

    }
}

