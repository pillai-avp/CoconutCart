package net.insi8.coconut.api.datasource

import net.insi8.coconut.api.CoconutAPI
import net.insi8.coconut.api.data.Cart
import net.insi8.coconut.api.extensions.toResult
import retrofit2.await
import retrofit2.awaitResponse
import net.insi8.coconut.api.data.common.Result

interface GroceriesCartDataSource {
    suspend fun getGroceriesCart() : Result<Cart>
}

class GroceriesCartRepository(private val coconutAPI: CoconutAPI) : GroceriesCartDataSource{
    override suspend fun getGroceriesCart(): Result<Cart> {
          return coconutAPI.getCart().awaitResponse().toResult()
    }
}