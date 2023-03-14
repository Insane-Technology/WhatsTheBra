package com.insane.whatsthebra.model

import com.insane.whatsthebra.database.dto.ImageDTO
import java.io.Serializable

class Image (
    val id: Int = 0,
    var name: String) : Serializable {

    fun toImageDTO(): ImageDTO {
        return ImageDTO(
            id = id,
            name = name
        )
    }
}