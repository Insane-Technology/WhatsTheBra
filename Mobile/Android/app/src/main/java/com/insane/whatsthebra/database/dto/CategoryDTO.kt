package com.insane.whatsthebra.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.insane.whatsthebra.model.Category

const val CATEGORY_TABLE_NAME = "tb_category"

@Entity(tableName = CATEGORY_TABLE_NAME)
data class CategoryDTO (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo val name: String
) {

    fun toCategory(): Category {
        return Category (
            id = id,
            name = name
        )
    }

}
