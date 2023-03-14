package com.insane.whatsthebra.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.insane.whatsthebra.model.ProductType

const val PRODUCT_TYPE_TABLE_NAME = "tb_product_type"

@Entity(tableName = PRODUCT_TYPE_TABLE_NAME)
data class ProductTypeDTO (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo val name: String
) {

    fun toProductType(): ProductType {
        return ProductType (
            id = id,
            name = name
        )
    }

}
