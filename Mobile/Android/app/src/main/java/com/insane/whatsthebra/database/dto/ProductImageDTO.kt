package com.insane.whatsthebra.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity

const val PRODUCT_IMAGE_TABLE_NAME = "tb_product_image"

@Entity(tableName = PRODUCT_IMAGE_TABLE_NAME, primaryKeys = ["id_product", "id_image"])
data class ProductImageDTO (
    @ColumnInfo val id_product: Int,
    @ColumnInfo val id_image: Int
)
