package com.insane.whatsthebra.service

import android.util.Log
import com.google.gson.JsonArray
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
    private var categories = ArrayList<Category>()
    private var braTypes = ArrayList<BraType>()
    private var products = ArrayList<Product>()
    private var user = User(id = 0)

    /**
     * Method to load data from API
     */
    fun loadData(callBack: DataCallBack) {
        loadUser(callBack)
        loadCategories(callBack)
        loadBraTypes(callBack)
        loadProducts(callBack)
    }

    private fun loadUser(callBack: DataCallBack) {
        setDataCallBack(callBack)
        // TODO Must Implement loading user from API getting credentials from shared preferences file
        // CREATING A FAKE USER TO TEST
        user = User(
            id = 1,
            name = "User Number One",
            email = "user@email.com",
            birthday = Date(),
            created = Date(),
            updated = Date(),
            city = City(),
            image = Image(),
            favouriteProducts = ArrayList()
        )

        // CALLING SERVICE DONE
        onServiceDone()
    }

    /**
     * Method to load category list from API
     * @param callBack an interface DataCallback must be implemented to receive the callback from this method
     */
    private fun loadCategories(callBack: DataCallBack) {
        categories.clear()
        setDataCallBack(callBack)
        val jsonCategories: Call<JsonArray> = apiService.fetchCategories()
        jsonCategories.enqueue(object : retrofit2.Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val jsonResponse = response.body()
                if (jsonResponse != null) {
                    for (c in jsonResponse) {
                        var category = Category(
                            c.asJsonObject.get("id").asInt,
                            c.asJsonObject.get("name").asString)
                        categories.add(category)
                    }
                    // TODO: AT THIS POINT WE MAY SAVE THE ENTIRE JSON RESPONSE INTO OUR SHARED PREFERENCES
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
     * Method to load bra type list from API
     * @param callBack an interface DataCallback must be implemented to receive the callback from this method
     */
    private fun loadBraTypes(callBack: DataCallBack) {
        setDataCallBack(callBack)
        braTypes.clear()
        val jsonBraTypeList: Call<JsonArray> = apiService.fetchBraTypes()
        jsonBraTypeList.enqueue(object : retrofit2.Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val jsonResponse = response.body()
                if (jsonResponse != null) {
                    for (t in jsonResponse) {
                        var braType = BraType(
                            t.asJsonObject.get("id").asInt,
                            t.asJsonObject.get("name").asString)
                        braTypes.add(braType)
                    }
                    // TODO: AT THIS POINT WE MAY SAVE THE ENTIRE JSON RESPONSE INTO OUR SHARED PREFERENCES
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
    fun loadProducts(callBack: DataCallBack) {
        setDataCallBack(callBack)
        products.clear()
        val jsonProducts: Call<JsonArray> = apiService.fetchProducts()
        jsonProducts.enqueue(object : retrofit2.Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val jsonResponse = response.body()
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
                        }

                        // SETTING PRODUCT BraType LIST
                        val braTypes: ArrayList<BraType> = ArrayList()
                        for (braType in p.asJsonObject.get("braTypes").asJsonArray) {
                            val bt = BraType(
                                braType.asJsonObject.get("id").asInt,
                                braType.asJsonObject.get("name").asString
                            )
                            braTypes.add(bt)
                        }

                        // SETTING PRODUCT CATEGORIES
                        val categories: ArrayList<Category> = ArrayList()
                        for (category in p.asJsonObject.get("categories").asJsonArray) {
                            val cat = Category(
                                category.asJsonObject.get("id").asInt,
                                category.asJsonObject.get("name").asString
                            )
                            categories.add(cat)
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
                        products.add(product)
                    }
                    // TODO: AT THIS POINT WE MAY SAVE THE ENTIRE JSON RESPONSE INTO OUR SHARED PREFERENCES
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
     * Method called when all services are done
     */
    private fun onServiceDone() {
        if (categories.size > 0 &&
            braTypes.size > 0 &&
            products.size > 0 &&
            user.id > 0
        )
        callBack!!.onDataLoaded()
    }

    /**
     * Private setter to set up the call back, it must be used by each method that will require a callback
     * @param dataCallBack it's a parameter from type DataCallBack
     */
    private fun setDataCallBack(dataCallBack: DataCallBack) {
        this.callBack = dataCallBack
    }

    fun getCategories(): ArrayList<Category> {
        return this.categories
    }

    fun getBraTypes(): ArrayList<BraType> {
        return this.braTypes
    }

    fun getProducts(): ArrayList<Product> {
        return this.products
    }

    fun getUser(): User {
        return user
    }

}
