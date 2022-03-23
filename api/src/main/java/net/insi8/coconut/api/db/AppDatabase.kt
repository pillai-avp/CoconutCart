package net.insi8.coconut.api.db

import androidx.room.Database
import androidx.room.RoomDatabase

const val DATABASE_NAME = "coconut_cart_database"

@Database(entities = [CartEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}