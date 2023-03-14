package com.insane.whatsthebra.service

import android.content.Context
import com.insane.whatsthebra.database.AppDataBase
import com.insane.whatsthebra.database.dto.FavouriteProductDTO
import com.insane.whatsthebra.model.Product
import com.insane.whatsthebra.model.User

class UserService (var context: Context) {

    private val db = AppDataBase.getDataBase(context)

    companion object {
        var user: User = User(id = 0)
    }

    /**
     * Method to set a user favourite product, saving it on local database
     * @return Boolean false when product is not anymore on the favourite list otherwise
     * returns true if product is on the favourite list.
     */
    fun setFavouriteProduct(product: Product): Boolean {
        return if (user.favouriteProducts.any { it == product }) {
            user.favouriteProducts.remove(product)
            db.favouriteProductDao().removeProductById(product.id)
            false
        } else {
            user.favouriteProducts.add(product)
            db.favouriteProductDao().insert(
                FavouriteProductDTO(
                    id_user = user.id,
                    id_product = product.id
                )
            )
            true
        }
    }

    /**
     * Method to retrieve all favourite products from local AppDataBase
     * This method will update the user favourite products list and will return the list as well
     */
    fun getFavouriteProducts(): List<Product> {
        user.favouriteProducts.clear()
        for (favouriteProduct in db.favouriteProductDao().getAll()) {
            user.favouriteProducts.add(db.productDao().getById(favouriteProduct.id_product).toProduct(context))
        }
        return user.favouriteProducts
    }

}