package com.insane.whatsthebra.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.insane.whatsthebra.model.BraType

const val BRA_TYPE_TABLE_NAME = "tb_bra_type"

@Entity(tableName = BRA_TYPE_TABLE_NAME)
data class BraTypeDTO (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo val name: String
) {

    fun toBraType(): BraType {
        return BraType (
            id = id,
            name = name
        )
    }

}
