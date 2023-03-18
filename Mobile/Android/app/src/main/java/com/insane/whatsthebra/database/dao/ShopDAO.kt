package com.insane.whatsthebra.database.dao

import androidx.room.*
import com.insane.whatsthebra.database.dto.PRODUCT_TYPE_TABLE_NAME
import com.insane.whatsthebra.database.dto.SHOP_TABLE_NAME
import com.insane.whatsthebra.database.dto.ShopDTO

@Dao
interface ShopDAO {

    @Query("SELECT * FROM $SHOP_TABLE_NAME ORDER BY name ASC")
    fun getAll(): List<ShopDTO>

    @Query("SELECT * FROM $SHOP_TABLE_NAME WHERE id = :shopId")
    fun getById(shopId: Int): ShopDTO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shop: ShopDTO)

    @Query("DELETE FROM $SHOP_TABLE_NAME")
    fun truncateTable()

}