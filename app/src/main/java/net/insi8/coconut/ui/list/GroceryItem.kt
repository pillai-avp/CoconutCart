package net.insi8.coconut.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import net.insi8.coconut.R
import net.insi8.coconut.api.data.Item
import timber.log.Timber

@OptIn(ExperimentalCoilApi::class)
@Composable
fun GroceryItem(cartItems: Item) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(
                    id = R.dimen.margin_normal
                ),
                vertical = dimensionResource(
                    id = R.dimen.margin_small
                )
            ), verticalAlignment = Alignment.CenterVertically
    ) {
        val product = cartItems.product
        val thumbnail = product.images.first().thumbnail
        Timber.d(thumbnail.url)
        Image(
            painter = rememberImagePainter(thumbnail.url, builder = {
                size(OriginalSize)
            }),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f)
                .padding(start = dimensionResource(id = R.dimen.margin_small))
        ) {
            Text(text = product.name, fontSize = 16.sp)
            Text(text = product.nameExtra, fontSize = 12.sp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.margin_small)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = product.grossPrice, fontSize = 16.sp)
                Text(
                    text = "${product.grossUnitPrice}/${product.unitPriceQuantityAbbreviation}",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.margin_small))
                )

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    if (cartItems.quantity == 0L) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_stepper_yellow_40),
                            contentDescription = null
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.ic_decrease_34),
                            contentDescription = null
                        )
                        Text(
                            text = cartItems.quantity.toString(),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(
                                horizontal = dimensionResource(
                                    id = R.dimen.margin_small
                                )
                            )
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_increase_34),
                            contentDescription = null
                        )
                    }
                }

            }
        }
    }
}

@Composable
@Preview
fun GroceryItemPreview() {

}