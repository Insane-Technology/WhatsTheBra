package com.insane.whatsthebra.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.insane.whatsthebra.database.AppDataBase
import com.insane.whatsthebra.model.*
import com.insane.whatsthebra.service.ProductService

const val PRODUCT_TABLE_NAME = "tb_product"

@Entity(tableName = PRODUCT_TABLE_NAME)
data class ProductDTO (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo val name: String,
    @ColumnInfo val price: Double,
    @ColumnInfo val description: String,
    @ColumnInfo var discount: Int = 0,
    @ColumnInfo val shopId: Int,
    @ColumnInfo val productTypeId: Int
) {

    /**
     * Method to convert ProductDTO into Product with all relations
     * This method requires a context to be able to use the AppDataBase
     */
    fun toProduct(db: AppDataBase): Product {
        return Product(
            id = id,
            name = name,
            price = price,
            description = description,
            discount = discount,
            shop = db.shopDao().getById(shopId).toShop(),
            productType = db.productTypeDao().getById(productTypeId).toProductType(),
            braTypes = ProductService(db).getProductBraTypeList(id),
            categories = ProductService(db).getProductCategoryList(id),
            images = ProductService(db).getProductImageList(id)
        )
    }

}
