package com.insane.whatsthebra.model

import com.insane.whatsthebra.database.dto.BraTypeDTO
import java.io.Serializable

class BraType(val id: Int = 0, var name: String) : Serializable {

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (other !is BraType) return false
        if (name != other.name || id != other.id) return false
        return true
    }

    fun toBraTypeDTO(): BraTypeDTO {
        return BraTypeDTO(
            id = id,
            name = name
        )
    }

}