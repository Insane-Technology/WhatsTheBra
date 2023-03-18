package com.insane.whatsthebra.database.dao

import androidx.room.*
import com.insane.whatsthebra.database.dto.BRA_TYPE_TABLE_NAME
import com.insane.whatsthebra.database.dto.FAVOURITE_PRODUCT_TABLE_NAME
import com.insane.whatsthebra.database.dto.FavouriteProductDTO

@Dao
interface FavouriteProductDAO {

    @Query("SELECT * FROM $FAVOURITE_PRODUCT_TABLE_NAME ORDER BY id_product ASC")
    fun getAll(): List<FavouriteProductDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favouriteProduct: FavouriteProductDTO)

    @Query("DELETE FROM $FAVOURITE_PRODUCT_TABLE_NAME WHERE id_product = :productId AND id_user = :userId")
    fun removeProductByIdAndUser(productId: Int, userId: Int)

    @Query("DELETE FROM $FAVOURITE_PRODUCT_TABLE_NAME")
    fun truncateTable()

}