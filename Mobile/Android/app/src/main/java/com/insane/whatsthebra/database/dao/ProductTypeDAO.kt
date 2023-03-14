package com.insane.whatsthebra.database.dao

import androidx.room.*
import com.insane.whatsthebra.database.dto.PRODUCT_TYPE_TABLE_NAME
import com.insane.whatsthebra.database.dto.ProductTypeDTO

@Dao
interface ProductTypeDAO {

    @Query("SELECT * FROM $PRODUCT_TYPE_TABLE_NAME ORDER BY name ASC")
    fun getAll(): List<ProductTypeDTO>

    @Query("SELECT * FROM $PRODUCT_TYPE_TABLE_NAME WHERE id = :productTypeId")
    fun getById(productTypeId: Int): ProductTypeDTO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productType: ProductTypeDTO)

}