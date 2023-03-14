package com.insane.whatsthebra.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity

const val FAVOURITE_PRODUCT_TABLE_NAME = "tb_favourite_product"

@Entity(tableName = FAVOURITE_PRODUCT_TABLE_NAME, primaryKeys = ["id_user", "id_product"])
data class FavouriteProductDTO (
    @ColumnInfo val id_user: Int,
    @ColumnInfo val id_product: Int
)
