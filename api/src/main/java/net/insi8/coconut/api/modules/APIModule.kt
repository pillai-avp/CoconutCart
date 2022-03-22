package net.insi8.coconut.api.modules

import com.google.gson.Gson
import net.insi8.coconut.api.CoconutAPI
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val CONNECTION_TIMEOUT_SEC = 40L
val apiModule = module {
    single {
        Gson()
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
            .build()
    }

    single<Retrofit> {
        val gson: Gson = get()
        Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    single<CoconutAPI> {
        val retrofit : Retrofit = get()
        retrofit.create(CoconutAPI::class.java)
    }
}
