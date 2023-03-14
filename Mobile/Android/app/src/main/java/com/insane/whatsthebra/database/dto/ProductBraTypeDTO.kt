package com.insane.whatsthebra.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val PRODUCT_BRA_TYPE_TABLE_NAME = "tb_product_bra_type"

@Entity(tableName = PRODUCT_BRA_TYPE_TABLE_NAME)
data class ProductBraTypeDTO (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo val id_product: Int,
    @ColumnInfo val id_bra_type: Int
)
