package net.insi8.coconut.api

import net.insi8.coconut.api.data.Cart
import retrofit2.Call
import retrofit2.http.GET

interface CoconutAPI {
    @GET("/oyvindbo/c615cf109b0eaf58cee527eb96d10c78/raw/35a117467e8b5f7ee8d24f6da34a539511c3ec0d/cart.json")
    fun getCart(): Call<Cart>
}