package net.insi8.coconut.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.insi8.coconut.R
import net.insi8.coconut.api.data.Cart
import net.insi8.coconut.api.data.Item
import net.insi8.coconut.ui.theme.CoconutTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.getViewModel

class GroceriesCartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ComposeView(this).apply {
            // Dispose the Composition when viewLifecycleOwner is destroyed
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(lifecycleOwner = this@GroceriesCartActivity)
            )
            setContent {
                CoconutTheme {
                    GroceriesCartContent()
                }
            }
        })
    }
}

@Composable
fun GroceriesCartContent() {
    val context = LocalContext.current
    val viewModel = getViewModel<GroceriesCartViewModel>()
    val viewState by viewModel.viewState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Cart", fontSize = 24.sp, color = Color.White)
            },
            elevation = 2.dp
        )
    }, content = { GroceriesCart(viewState) })

}

@Composable
fun GroceriesCart(state: GroceriesCartState) {
    when (state) {
        is GroceriesCartState.Done -> GroceriesCartList(state.cart)
        is GroceriesCartState.Loading -> GroceriesCartLoading()
    }
}

@Composable
fun GroceriesCartLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.loading))
    }
}

@Composable
fun GroceriesCartList(cart: Cart) {
    LazyColumn(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_large))) {
        items(cart.items) { cartItems ->
            GroceryItem(cartItems)
        }
    }
}

@Preview
@Composable
fun GroceriesCartLoadingPreview() {
    CoconutTheme {
        GroceriesCartLoading()
    }
}
