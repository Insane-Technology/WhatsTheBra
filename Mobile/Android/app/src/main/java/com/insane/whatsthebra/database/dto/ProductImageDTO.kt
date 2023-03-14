package com.insane.whatsthebra.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val PRODUCT_IMAGE_TABLE_NAME = "tb_product_image"

@Entity(tableName = PRODUCT_IMAGE_TABLE_NAME)
data class ProductImageDTO (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo val id_product: Int,
    @ColumnInfo val id_image: Int
)
