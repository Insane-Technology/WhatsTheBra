package com.insane.whatsthebra.database.dao

import androidx.room.*
import com.insane.whatsthebra.database.dto.PRODUCT_CATEGORY_TABLE_NAME
import com.insane.whatsthebra.database.dto.PRODUCT_TABLE_NAME
import com.insane.whatsthebra.database.dto.ProductDTO

@Dao
interface ProductDAO {

    @Query("SELECT * FROM $PRODUCT_TABLE_NAME ORDER BY name ASC")
    fun getAll(): List<ProductDTO>

    @Query("SELECT * FROM $PRODUCT_TABLE_NAME WHERE productTypeId = :productTypeId ORDER BY id ASC")
    fun getAllByType(productTypeId: Int): List<ProductDTO>

    @Query("SELECT * FROM $PRODUCT_TABLE_NAME WHERE id = :productId")
    fun getById(productId: Int): ProductDTO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: ProductDTO)

    @Query("DELETE FROM $PRODUCT_TABLE_NAME")
    fun truncateTable()

}