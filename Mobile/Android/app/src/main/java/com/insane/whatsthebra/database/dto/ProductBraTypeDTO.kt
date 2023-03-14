package com.insane.whatsthebra.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity

const val PRODUCT_BRA_TYPE_TABLE_NAME = "tb_product_bra_type"

@Entity(tableName = PRODUCT_BRA_TYPE_TABLE_NAME, primaryKeys = ["id_product", "id_bra_type"])
data class ProductBraTypeDTO (
    @ColumnInfo val id_product: Int,
    @ColumnInfo val id_bra_type: Int
)
