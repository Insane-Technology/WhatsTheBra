package com.insane.whatsthebra.model

class Product (
    val id: Int = 0,
    val name: String? = null,
    val price: Double? = null,
    val description: String? = null,
    val discount: Int? = null,
    val shop: Shop? = null,
    val productType: ProductType? = null,
    val braTypes: List<BraType> = ArrayList(),
    val categories: List<Category> = ArrayList(),
    val images: List<Image> = ArrayList()) {}