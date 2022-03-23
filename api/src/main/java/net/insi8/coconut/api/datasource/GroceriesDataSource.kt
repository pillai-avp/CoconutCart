package net.insi8.coconut.api.datasource

import kotlinx.coroutines.delay
import net.insi8.coconut.api.CoconutAPI
import net.insi8.coconut.api.data.Cart
import net.insi8.coconut.api.data.common.Result
import net.insi8.coconut.api.extensions.toResult
import retrofit2.awaitResponse

interface GroceriesCartDataSource {
    suspend fun getGroceriesCart(): Result<Cart>
    suspend fun updateCart(productId: Long, updatedQuantity: Long): Result<Unit>
}

class GroceriesCartRepository(private val coconutAPI: CoconutAPI) : GroceriesCartDataSource {
    override suspend fun getGroceriesCart(): Result<Cart> {
        return coconutAPI.getCart().awaitResponse().toResult()
    }

    override suspend fun updateCart(productId: Long, updatedQuantity: Long): Result<Unit> {
        delay(800L)
        return Result.withEmptySuccess()
    }
}