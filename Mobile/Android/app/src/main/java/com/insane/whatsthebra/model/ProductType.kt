package com.insane.whatsthebra.model

import com.insane.whatsthebra.database.dto.ProductTypeDTO
import java.io.Serializable

class ProductType(val id: Int = 0, var name: String) : Serializable {

    fun toProductTypeDTO(): ProductTypeDTO {
        return ProductTypeDTO (
            id = id,
            name = name
        )
    }

}