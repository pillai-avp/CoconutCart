package net.insi8.coconut.api.db

import androidx.room.*

@Entity(tableName = "groceries_cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = false) val product_id: Long,
    val quantity: Long,
    val synced: Boolean = true
)


@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CartEntity)

    @Query("Select * from groceries_cart")
    suspend fun gelAllCartItems(): List<CartEntity>

    @Update
    suspend fun updateItem(cart: CartEntity)

    @Delete
    suspend fun deleteItem(cart: CartEntity)
}