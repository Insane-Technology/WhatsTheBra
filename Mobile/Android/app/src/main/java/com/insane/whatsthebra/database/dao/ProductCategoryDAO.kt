package com.insane.whatsthebra.database.dao

import androidx.room.*
import com.insane.whatsthebra.database.dto.PRODUCT_CATEGORY_TABLE_NAME
import com.insane.whatsthebra.database.dto.ProductCategoryDTO

@Dao
interface ProductCategoryDAO {

    @Query("SELECT * FROM $PRODUCT_CATEGORY_TABLE_NAME ORDER BY id_category ASC")
    fun getAll(): List<ProductCategoryDTO>

    @Query("SELECT * FROM $PRODUCT_CATEGORY_TABLE_NAME WHERE id_product = :productId ORDER BY id_category ASC")
    fun getAllByProductId(productId: Int): List<ProductCategoryDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productCategory: ProductCategoryDTO)

}