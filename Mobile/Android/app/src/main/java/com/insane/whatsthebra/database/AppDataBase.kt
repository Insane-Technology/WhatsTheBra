package com.insane.whatsthebra.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.insane.whatsthebra.activity.MainActivity
import com.insane.whatsthebra.database.dao.*
import com.insane.whatsthebra.database.dto.*

const val DATABASE_NAME = "db_insane"

@Database(entities = [
    ProductDTO::class,
    ShopDTO::class,
    FavouriteProductDTO::class,
    ProductBraTypeDTO::class,
    ProductTypeDTO::class,
    BraTypeDTO::class,
    CategoryDTO::class,
    ImageDTO::class,
    ProductCategoryDTO::class,
    ProductImageDTO::class],
    version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun productDao(): ProductDAO
    abstract fun shopDao(): ShopDAO
    abstract fun productTypeDao(): ProductTypeDAO
    abstract fun favouriteProductDao(): FavouriteProductDAO
    abstract fun productBraTypeDao(): ProductBraTypeDAO
    abstract fun productCategoryDao(): ProductCategoryDAO
    abstract fun productImageDao(): ProductImageDAO
    abstract fun braTypeDao(): BraTypeDAO
    abstract fun categoryDao(): CategoryDAO
    abstract fun imageDao(): ImageDAO

    companion object {
        private var INSTANCE: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    AppDataBase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}