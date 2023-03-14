package com.insane.whatsthebra.service

import com.insane.whatsthebra.database.AppDataBase
import com.insane.whatsthebra.model.BraType
import com.insane.whatsthebra.model.Category
import com.insane.whatsthebra.model.Image

class ProductService (var db: AppDataBase) {

    fun getProductBraTypeList(productId: Int): ArrayList<BraType> {
        val braTypes = ArrayList<BraType>()
        for (braType in db.productBraTypeDao().getAllByProductId(productId)) {
            braTypes.add(db.braTypeDao().getById(braType.id_bra_type).toBraType())
        }
        return braTypes
    }

    fun getProductCategoryList(productId: Int): ArrayList<Category> {
        val categories = ArrayList<Category>()
        for (category in db.productCategoryDao().getAllByProductId(productId)) {
            categories.add(db.categoryDao().getById(category.id_category).toCategory())
        }
        return categories
    }

    fun getProductImageList(productId: Int): ArrayList<Image> {
        val images = ArrayList<Image>()
        for (image in db.productImageDao().getAllByProductId(productId)) {
            images.add(db.imageDao().getById(image.id_image).toImage())
        }
        return images
    }

}