package net.insi8.coconut.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
fun GroceryItem(cartItems: Item, onUpdateCart: ((Long, Long) -> Unit)? = null) {
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
            ProductName(product.name, product.nameExtra)
            PriceAndQuantity(
                product.grossPrice,
                product.grossUnitPrice,
                product.unitPriceQuantityAbbreviation,
                cartItems.quantity,
                onUpdateCart = { quantity ->
                    if (onUpdateCart != null) {
                        onUpdateCart(product.id, quantity)
                    }
                }
            )
        }
    }
}

@Composable
private fun ProductName(name: String, nameExtra: String) {
    Text(text = name, fontSize = 16.sp)
    Text(text = nameExtra, fontSize = 12.sp)
}

@Composable
private fun PriceAndQuantity(
    grossPrice: String,
    grossUnitPrice: String,
    unitPriceQuantityAbbreviation: String,
    quantity: Long,
    onUpdateCart: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = grossPrice, fontSize = 16.sp)
        Text(
            text = "${grossUnitPrice}/${unitPriceQuantityAbbreviation}",
            fontSize = 12.sp,
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.margin_small))
        )
        ItemQuantity(
            modifier = Modifier.weight(1f),
            quantity = quantity,
            onUpdateCart = onUpdateCart
        )

    }
}

@Composable
private fun ItemQuantity(modifier: Modifier, quantity: Long, onUpdateCart: (Long) -> Unit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        if (quantity == 0L) {
            Image(
                painter = painterResource(id = R.drawable.ic_stepper_yellow_40),
                contentDescription = null,
                Modifier.clickable {
                    onUpdateCart.invoke(quantity + 1)
                }
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_decrease_34),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onUpdateCart.invoke(quantity - 1)
                }
            )
            Text(
                text = quantity.toString(),
                fontSize = 14.sp,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen.margin_small
                    )
                )
            )
            Image(
                painter = painterResource(id = R.drawable.ic_increase_34),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onUpdateCart.invoke(quantity + 1)
                }
            )
        }
    }
}

@Composable
fun ShowMeTheMoneyListItem(money: ShowMeTheMoney) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.margin_normal),
                vertical = dimensionResource(
                    id = R.dimen.margin_small
                )
            )
            .defaultMinSize(minHeight = 46.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${money.totalNumberOfItems} Items", modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Text(
            text = "Total --- ${money.grandTotalPrice} Kr",
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun GroceryItemPreview() {
    Column {

        ProductName(name = "Greek Yogurt", nameExtra = "Greek 750 g")
        PriceAndQuantity(
            grossPrice = "33.00",
            grossUnitPrice = "44.00",
            unitPriceQuantityAbbreviation = "kg",
            quantity = 1L,
            onUpdateCart = { quantity ->

            }
        )
        Spacer(modifier = Modifier.height(24.dp))

        ProductName(name = "Greek Yogurt", nameExtra = "Greek 750 g")
        PriceAndQuantity(
            grossPrice = "33.00",
            grossUnitPrice = "44.00",
            unitPriceQuantityAbbreviation = "kg",
            quantity = 0L,
            onUpdateCart = { quantity ->
            }
        )

        ShowMeTheMoneyListItem(ShowMeTheMoney(6, "666.66"))
    }
}