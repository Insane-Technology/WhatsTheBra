package com.insane.whatsthebra.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.insane.whatsthebra.database.dao.FavouriteProductDAO
import com.insane.whatsthebra.database.dao.ProductDAO
import com.insane.whatsthebra.database.dao.ProductTypeDAO
import com.insane.whatsthebra.database.dao.ShopDAO
import com.insane.whatsthebra.database.dto.FavouriteProductDTO
import com.insane.whatsthebra.database.dto.ProductDTO
import com.insane.whatsthebra.database.dto.ProductTypeDTO
import com.insane.whatsthebra.database.dto.ShopDTO

const val DATABASE_NAME = "db_insane"

@Database(entities = [
    ProductDTO::class,
    ShopDTO::class,
    FavouriteProductDTO::class,
    ProductTypeDTO::class],
    version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun productDao(): ProductDAO
    abstract fun shopDao(): ShopDAO
    abstract fun productTypeDao(): ProductTypeDAO
    abstract fun favouriteProductDao(): FavouriteProductDAO

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