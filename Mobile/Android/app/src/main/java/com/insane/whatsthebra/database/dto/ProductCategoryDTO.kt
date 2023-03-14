package com.insane.whatsthebra.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity

const val PRODUCT_CATEGORY_TABLE_NAME = "tb_product_category"

@Entity(tableName = PRODUCT_CATEGORY_TABLE_NAME, primaryKeys = ["id_product", "id_category"])
data class ProductCategoryDTO (
    @ColumnInfo val id_product: Int,
    @ColumnInfo val id_category: Int
)
