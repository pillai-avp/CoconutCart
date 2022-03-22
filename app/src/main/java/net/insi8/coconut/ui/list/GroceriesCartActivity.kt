package net.insi8.coconut.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.insi8.coconut.R
import net.insi8.coconut.api.data.Cart
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.getViewModel

class GroceriesCartActivity : AppCompatActivity() {

    private val viewModel: GroceriesCartViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ComposeView(this).apply {
            // Dispose the Composition when viewLifecycleOwner is destroyed
            /*setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(lifecycleOwner = this@GroceriesCartActivity)
            )*/
            setContent {
                GroceriesCartLoading()
            }
        })
    }
}

@Composable
fun GroceriesCartContent() {
    val context = LocalContext.current
    val viewModel = getViewModel<GroceriesCartViewModel>()
    val viewState by viewModel.viewState.collectAsState()

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
    TODO("Not yet implemented")
}

@Preview
@Composable
fun GroceriesCartLoadingPreview() {
    GroceriesCartLoading()
}
