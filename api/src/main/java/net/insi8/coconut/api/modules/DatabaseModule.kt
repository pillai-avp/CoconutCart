package net.insi8.coconut.api.modules

import androidx.room.Room
import net.insi8.coconut.api.db.AppDatabase
import net.insi8.coconut.api.db.DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataBaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication().applicationContext,
            AppDatabase::class.java, DATABASE_NAME
        ).build()
    }

    single {
        get<AppDatabase>().cartDao()
    }
}