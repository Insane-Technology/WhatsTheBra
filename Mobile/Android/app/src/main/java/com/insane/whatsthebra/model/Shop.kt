package com.insane.whatsthebra.model

import com.insane.whatsthebra.database.dto.ShopDTO
import java.io.Serializable

class Shop (val id: Int = 0, var name: String, var link: String) : Serializable {

    fun toShopDTO(): ShopDTO {
        return ShopDTO (
            id = id,
            name = name,
            link = link
        )
    }

}