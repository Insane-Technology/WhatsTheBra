package com.insane.whatsthebra.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.insane.whatsthebra.model.Shop

const val SHOP_TABLE_NAME = "tb_shop"

@Entity(tableName = SHOP_TABLE_NAME)
data class ShopDTO (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo val name: String,
    @ColumnInfo val link: String
) {

    fun toShop(): Shop {
        return Shop (
            id = id,
            name = name,
            link = link
        )
    }

}
