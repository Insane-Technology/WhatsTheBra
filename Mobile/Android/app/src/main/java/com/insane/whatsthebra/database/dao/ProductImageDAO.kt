package com.insane.whatsthebra.database.dao

import androidx.room.*
import com.insane.whatsthebra.database.dto.PRODUCT_IMAGE_TABLE_NAME
import com.insane.whatsthebra.database.dto.ProductImageDTO

@Dao
interface ProductImageDAO {

    @Query("SELECT * FROM $PRODUCT_IMAGE_TABLE_NAME ORDER BY id_image ASC")
    fun getAll(): List<ProductImageDTO>

    @Query("SELECT * FROM $PRODUCT_IMAGE_TABLE_NAME WHERE id_product = :productId ORDER BY id_image ASC")
    fun getAllByProductId(productId: Int): List<ProductImageDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productImage: ProductImageDTO)

}