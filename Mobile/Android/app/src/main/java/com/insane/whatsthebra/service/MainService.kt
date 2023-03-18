package com.insane.whatsthebra.service

import android.util.Log
import com.google.gson.JsonArray
import com.insane.whatsthebra.database.AppDataBase
import com.insane.whatsthebra.database.dto.ProductBraTypeDTO
import com.insane.whatsthebra.database.dto.ProductCategoryDTO
import com.insane.whatsthebra.database.dto.ProductImageDTO
import com.insane.whatsthebra.interfaces.ApiInterface
import com.insane.whatsthebra.interfaces.DataCallBack
import com.insane.whatsthebra.model.*
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

/**
 * SingleTon object class
 * This object will hold all services data from MainActivity
 * Use the getters to obtain the data and the load methods to update data
 */
object MainService {

    private val apiService = ApiInterface.create()
    private var callBack: DataCallBack? = null
    private var serviceCounter = 0

    /**
     * Method to load data from API
     */
    fun loadData(db: AppDataBase, callBack: DataCallBack) {
        loadUser(callBack)
        loadCategories(db, callBack)
        loadBraTypes(db, callBack)
        loadImages(db, callBack)
        loadShopList(db, callBack)
        loadProductTypeList(db, callBack)
        loadProducts(db, callBack)
    }

    fun loadDataOffline(db: AppDataBase, callBack: DataCallBack) {
        loadUser(callBack)
        onServiceDone()
    }

    private fun loadUser(callBack: DataCallBack) {
        // serviceCounter++
        setDataCallBack(callBack)
        // TODO Must Implement loading user from API getting credentials from shared preferences file
        // CREATING A FAKE USER TO TEST
        UserService.user = User(
            id = 1,
            name = "User Number One",
            email = "user@email.com",
            birthday = Date(),
            created = Date(),
            updated = Date(),
            city = City(),
            image = Image(name = ""),
            favouriteProducts = ArrayList()
        )

        // CALLING SERVICE DONE
        // onServiceDone()
    }

    /**
     * Method to load category list from API
     * @param callBack an interface DataCallback must be implemented to receive the callback from this method
     */
    private fun loadCategories(db: AppDataBase, callBack: DataCallBack) {
        serviceCounter++
        setDataCallBack(callBack)
        val jsonCategories: Call<JsonArray> = apiService.fetchCategories()
        jsonCategories.enqueue(object : retrofit2.Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val jsonResponse = response.body()
                db.categoryDao().truncateTable()
                if (jsonResponse != null) {
                    for (c in jsonResponse) {
                        var category = Category(
                            c.asJsonObject.get("id").asInt,
                            c.asJsonObject.get("name").asString)
                        db.categoryDao().insert(category.toCategoryDTO())
                    }
                    onServiceDone()
                }
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.message?.let {
                    Log.e("API", it)
                }
            }
        })
    }

    /**
     * Method to load bra type list from API
     * @param callBack an interface DataCallback must be implemented to receive the callback from this method
     */
    private fun loadBraTypes(db: AppDataBase, callBack: DataCallBack) {
        serviceCounter++
        setDataCallBack(callBack)
        val jsonBraTypeList: Call<JsonArray> = apiService.fetchBraTypes()
        jsonBraTypeList.enqueue(object : retrofit2.Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val jsonResponse = response.body()
                db.braTypeDao().truncateTable()
                if (jsonResponse != null) {
                    for (t in jsonResponse) {
                        var braType = BraType(
                            t.asJsonObject.get("id").asInt,
                            t.asJsonObject.get("name").asString)
                        db.braTypeDao().insert(braType.toBraTypeDTO())
                    }
                    onServiceDone()
                }
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.message?.let {
                    // TODO: CHECK IF THERE IS ANY JSON RESPONSE SAVED IN SHARED PREFERENCES TO CONTINUE WITH APP
                    Log.e("API", it)
                }
            }
        })
    }

    /**
     * Method to load a image list from API
     * @param callBack an interface DataCallback must be implemented to receive the callback from this method
     */
    private fun loadImages(db: AppDataBase, callBack: DataCallBack) {
        serviceCounter++
        setDataCallBack(callBack)
        val jsonImageList: Call<JsonArray> = apiService.fetchImages()
        jsonImageList.enqueue(object : retrofit2.Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val jsonResponse = response.body()
                db.imageDao().truncateTable()
                if (jsonResponse != null) {
                    for (t in jsonResponse) {
                        var image = Image (
                            t.asJsonObject.get("id").asInt,
                            t.asJsonObject.get("name").asString)
                        db.imageDao().insert(image.toImageDTO())
                    }
                    onServiceDone()
                }
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.message?.let {
                    // TODO: CHECK IF THERE IS ANY JSON RESPONSE SAVED IN SHARED PREFERENCES TO CONTINUE WITH APP
                    Log.e("API", it)
                }
            }
        })
    }

    /**
     * Method to load shop list from API
     * @param callBack an interface DataCallback must be implemented to receive the callback from this method
     */
    private fun loadShopList(db: AppDataBase, callBack: DataCallBack) {
        serviceCounter++
        setDataCallBack(callBack)
        val jsonShopList: Call<JsonArray> = apiService.fetchShops()
        jsonShopList.enqueue(object : retrofit2.Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val jsonResponse = response.body()
                db.shopDao().truncateTable()
                if (jsonResponse != null) {
                    for (t in jsonResponse) {
                        var shop = Shop (
                            t.asJsonObject.get("id").asInt,
                            t.asJsonObject.get("name").asString,
                            t.asJsonObject.get("link").asString)
                        db.shopDao().insert(shop.toShopDTO())
                    }
                    onServiceDone()
                }
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.message?.let {
                    // TODO: CHECK IF THERE IS ANY JSON RESPONSE SAVED IN SHARED PREFERENCES TO CONTINUE WITH APP
                    Log.e("API", it)
                }
            }
        })
    }

    /**
     * Method to load product type list from API
     * @param callBack an interface DataCallback must be implemented to receive the callback from this method
     */
    private fun loadProductTypeList(db: AppDataBase, callBack: DataCallBack) {
        serviceCounter++
        setDataCallBack(callBack)
        val jsonProductTypeList: Call<JsonArray> = apiService.fetchProductTypes()
        jsonProductTypeList.enqueue(object : retrofit2.Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val jsonResponse = response.body()
                db.productTypeDao().truncateTable()
                if (jsonResponse != null) {
                    for (t in jsonResponse) {
                        var productType = ProductType (
                            t.asJsonObject.get("id").asInt,
                            t.asJsonObject.get("name").asString)
                        db.productTypeDao().insert(productType.toProductTypeDTO())
                    }
                    onServiceDone()
                }
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.message?.let {
                    // TODO: CHECK IF THERE IS ANY JSON RESPONSE SAVED IN SHARED PREFERENCES TO CONTINUE WITH APP
                    Log.e("API", it)
                }
            }
        })
    }

    /**
     * Method to load products list from API
     * @param callBack an interface DataCallback must be implemented to receive the callback from this method
     */
    fun loadProducts(db: AppDataBase, callBack: DataCallBack) {
        serviceCounter++
        setDataCallBack(callBack)
        val jsonProducts: Call<JsonArray> = apiService.fetchProducts()
        jsonProducts.enqueue(object : retrofit2.Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val jsonResponse = response.body()
                db.productDao().truncateTable()
                db.productImageDao().truncateTable()
                db.productBraTypeDao().truncateTable()
                db.productCategoryDao().truncateTable()
                if (jsonResponse != null) {
                    for (p in jsonResponse) {

                        // SETTING PRODUCT IMAGE LIST
                        val imageList: ArrayList<Image> = ArrayList()
                        for (image in p.asJsonObject.get("images").asJsonArray) {
                            val img = Image(
                                image.asJsonObject.get("id").asInt,
                                image.asJsonObject.get("name").asString
                            )
                            imageList.add(img)
                            db.productImageDao().insert(ProductImageDTO(id_product = p.asJsonObject.get("id").asInt, id_image = img.id))
                        }

                        // SETTING PRODUCT BraType LIST
                        val braTypes: ArrayList<BraType> = ArrayList()
                        for (braType in p.asJsonObject.get("braTypes").asJsonArray) {
                            val bt = BraType(
                                braType.asJsonObject.get("id").asInt,
                                braType.asJsonObject.get("name").asString
                            )
                            braTypes.add(bt)
                            db.productBraTypeDao().insert(ProductBraTypeDTO(id_product = p.asJsonObject.get("id").asInt, id_bra_type = bt.id))
                        }

                        // SETTING PRODUCT CATEGORIES
                        val categories: ArrayList<Category> = ArrayList()
                        for (category in p.asJsonObject.get("categories").asJsonArray) {
                            val cat = Category(
                                category.asJsonObject.get("id").asInt,
                                category.asJsonObject.get("name").asString
                            )
                            categories.add(cat)
                            db.productCategoryDao().insert(ProductCategoryDTO(id_product = p.asJsonObject.get("id").asInt, id_category = cat.id))
                        }

                        // SETTING PRODUCT SHOP
                        val shopJsonObj = p.asJsonObject.get("shop").asJsonObject
                        val shop = Shop(
                            shopJsonObj.asJsonObject.get("id").asInt,
                            shopJsonObj.asJsonObject.get("name").asString,
                            shopJsonObj.asJsonObject.get("link").asString
                        )

                        // SETTING PRODUCT TYPE
                        val productTypeJsonObject = p.asJsonObject.get("productType").asJsonObject
                        val productType = ProductType(
                            productTypeJsonObject.asJsonObject.get("id").asInt,
                            productTypeJsonObject.asJsonObject.get("name").asString
                        )

                        val product = Product(
                            p.asJsonObject.get("id").asInt,
                            p.asJsonObject.get("name").asString,
                            p.asJsonObject.get("price").asDouble,
                            p.asJsonObject.get("description").asString,
                            p.asJsonObject.get("discount").asInt,
                            shop,
                            productType,
                            braTypes,
                            categories,
                            imageList
                        )

                        db.productDao().insert(product.toProductDTO())
                    }
                    onServiceDone()
                }
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.message?.let {
                    // TODO: CHECK IF THERE IS ANY JSON RESPONSE SAVED IN SHARED PREFERENCES TO CONTINUE WITH APP
                    Log.e("API", it)
                }
            }
        })
        // TODO: WE MUST CHECK IF THERE IS INTERNET BEFORE LOAD DATA AND SET A TIMEOUT TO CALL onServiceDOne
    }

    /**
     * Method called when all services are done
     */
    private fun onServiceDone() {
        serviceCounter--
        if (serviceCounter <= 0) {
            callBack!!.onDataLoaded()
        }
    }

    /**
     * Private setter to set up the call back, it must be used by each method that will require a callback
     * @param dataCallBack it's a parameter from type DataCallBack
     */
    private fun setDataCallBack(dataCallBack: DataCallBack) {
        this.callBack = dataCallBack
    }

}
