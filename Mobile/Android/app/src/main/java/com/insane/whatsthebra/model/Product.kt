package com.insane.whatsthebra.model

class Product (
    val id: Int = 0,
    val name: String? = null,
    val price: Double? = null,
    val description: String? = null,
    val discount: Int? = null,
    val shop: Shop? = null,
    val productType: ProductType? = null,
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
}