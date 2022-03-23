package net.insi8.coconut.api.datasource

import kotlinx.coroutines.delay
import net.insi8.coconut.api.CoconutAPI
import net.insi8.coconut.api.data.Cart
import net.insi8.coconut.api.data.common.Result
import net.insi8.coconut.api.extensions.toResult
import retrofit2.awaitResponse

interface GroceriesCartDataSource {
    suspend fun getGroceriesCart(): Result<Cart>
    suspend fun updateCart(cartItems: Map<Long, Long>): Result<Unit>
}

class GroceriesCartRepository(private val coconutAPI: CoconutAPI) : GroceriesCartDataSource {
    override suspend fun getGroceriesCart(): Result<Cart> {
        return coconutAPI.getCart().awaitResponse().toResult()
    }

    /**
     * I asssume the update cart will return the list of cart items.
     */
    override suspend fun updateCart(cartItems: Map<Long, Long>): Result<Unit> {
        delay(800L)
        return Result.withEmptySuccess()
    }
}