package com.insane.whatsthebra.model

import com.insane.whatsthebra.database.dto.ProductDTO

data class Product (
    var id: Int = 0,
    val name: String,
    val price: Double,
    val description: String,
    var discount: Int = 0,
    val shop: Shop,
    val productType: ProductType,
    val braTypes: ArrayList<BraType> = ArrayList(),
    val categories: ArrayList<Category> = ArrayList(),
    val images: ArrayList<Image> = ArrayList()) {

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (other !is Product) return false
        if (name != other.name || id != other.id) return false
        return true
    }

    fun toProductDTO(): ProductDTO {
        return ProductDTO (
            id = id,
            name = name,
            price = price,
            description = description,
            discount = discount,
            shopId = shop.id,
            productTypeId = productType.id
        )
    }

}