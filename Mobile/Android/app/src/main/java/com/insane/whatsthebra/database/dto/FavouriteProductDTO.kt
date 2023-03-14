package com.insane.whatsthebra.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val FAVOURITE_PRODUCT_TABLE_NAME = "tb_favourite_product"

@Entity(tableName = FAVOURITE_PRODUCT_TABLE_NAME)
data class FavouriteProductDTO (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo val id_user: Int,
    @ColumnInfo val id_product: Int
)
