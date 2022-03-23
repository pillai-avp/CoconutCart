package net.insi8.coconut.api.datasource

import kotlinx.coroutines.delay
import net.insi8.coconut.api.CoconutAPI
import net.insi8.coconut.api.data.Cart
import net.insi8.coconut.api.data.Item
import net.insi8.coconut.api.data.common.Result
import net.insi8.coconut.api.extensions.toResult
import retrofit2.awaitResponse

interface GroceriesCartDataSource {
    suspend fun getGroceriesCart(): Result<Cart>
    suspend fun updateCart(cartItems: Map<Long, Long>): Result<List<Item>>
}

class GroceriesCartRepository(private val coconutAPI: CoconutAPI) : GroceriesCartDataSource {
    override suspend fun getGroceriesCart(): Result<Cart> {
        return coconutAPI.getCart().awaitResponse().toResult()
    }

    override suspend fun updateCart(cartItems: Map<Long, Long>): Result<List<Item>> {
        delay(800L)
        return Result.successIfNotNull(emptyList<Item>())
    }
}