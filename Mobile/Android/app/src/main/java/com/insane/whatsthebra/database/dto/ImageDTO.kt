package com.insane.whatsthebra.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.insane.whatsthebra.model.Image

const val IMAGE_TABLE_NAME = "tb_image"

@Entity(tableName = IMAGE_TABLE_NAME)
data class ImageDTO (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo val name: String
) {

    fun toImage(): Image {
        return Image (
            id = id,
            name = name
        )
    }

}
