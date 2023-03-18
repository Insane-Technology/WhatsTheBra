package com.insane.whatsthebra.database.dao

import androidx.room.*
import com.insane.whatsthebra.database.dto.IMAGE_TABLE_NAME
import com.insane.whatsthebra.database.dto.PRODUCT_BRA_TYPE_TABLE_NAME
import com.insane.whatsthebra.database.dto.ProductBraTypeDTO

@Dao
interface ProductBraTypeDAO {

    @Query("SELECT * FROM $PRODUCT_BRA_TYPE_TABLE_NAME ORDER BY id_bra_type ASC")
    fun getAll(): List<ProductBraTypeDTO>

    @Query("SELECT * FROM $PRODUCT_BRA_TYPE_TABLE_NAME WHERE id_product = :productId ORDER BY id_bra_type ASC")
    fun getAllByProductId(productId: Int): List<ProductBraTypeDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productBraType: ProductBraTypeDTO)

    @Query("DELETE FROM $PRODUCT_BRA_TYPE_TABLE_NAME")
    fun truncateTable()

}