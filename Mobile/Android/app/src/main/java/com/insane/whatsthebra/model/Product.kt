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
    val images: ArrayList<Image> = ArrayList()) {}