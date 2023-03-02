package com.insane.whatsthebra.service

import android.util.Log
import com.google.gson.JsonArray
import com.insane.whatsthebra.interfaces.ApiInterface
import com.insane.whatsthebra.interfaces.DataCallBack
import com.insane.whatsthebra.model.*
import retrofit2.Call
import retrofit2.Response
import java.lang.reflect.Array

/**
 * SingleTon object class
 * This object will hold all services data from MainActivity
 * Use the getters to obtain the data and the load methods to update data
 */
object MainService {

    private val apiService = ApiInterface.create()
    private var callBack: DataCallBack? = null
    private var categoryLoaded: Boolean = false
    private var braTypeLoaded: Boolean = false
    private var productLoaded: Boolean = false
    private var categories = ArrayList<Category>()
    private var braTypes = ArrayList<BraType>()
    private var products = ArrayList<Product>()

    /**
     * Method to load data from API
     */
    fun loadData(callBack: DataCallBack) {
        loadCategories(callBack)
        loadBraTypes(callBack)
        loadProducts(callBack)
    }

    /**
     * Method to load category list from API
     * @param callBack an interface DataCallback must be implemented to receive the callback from this method
     */
    fun loadCategories(callBack: DataCallBack) {
        setDataCallBack(callBack)
        categoryLoaded = false
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
                    categoryLoaded = true
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
    fun loadBraTypes(callBack: DataCallBack) {
        setDataCallBack(callBack)
        braTypeLoaded = false
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
                    braTypeLoaded = true
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
        productLoaded = false
        val jsonProducts: Call<JsonArray> = apiService.fetchProducts()
        jsonProducts.enqueue(object : retrofit2.Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val jsonResponse = response.body()
                if (jsonResponse != null) {
                    for (p in jsonResponse) {
                        var imageList: ArrayList<Image> = ArrayList()
                        for (image in p.asJsonObject.get("images").asJsonArray) {
                            val img = Image(
                                image.asJsonObject.get("id").asInt,
                                image.asJsonObject.get("name").asString
                            )
                            imageList.add(img)
                        }
                        // TODO FALTA ADICIONAR O SHOP, PRODUCT TYPE, LIST BRATYPE E LIST CATEGORY
                        // TODO FAZER IGUAL A LISTA DE IMAGES

                        var product = Product(
                            p.asJsonObject.get("id").asInt,
                            p.asJsonObject.get("name").asString,
                            p.asJsonObject.get("price").asDouble,
                            p.asJsonObject.get("description").asString,
                            p.asJsonObject.get("discount").asInt,
                            null,
                            null,
                            ArrayList<BraType>(),
                            ArrayList<Category>(),
                            imageList
                        )
                        products.add(product)
                    }
                    // TODO: AT THIS POINT WE MAY SAVE THE ENTIRE JSON RESPONSE INTO OUR SHARED PREFERENCES
                    productLoaded = true
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
        if (categoryLoaded && braTypeLoaded && productLoaded) {
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

    fun getCategories(): ArrayList<Category> {
        return this.categories
    }

    fun getBraTypes(): ArrayList<BraType> {
        return this.braTypes
    }

    fun getProducts(): ArrayList<Product> {
        return this.products
    }

}

//                        p.asJsonObject.get("shop").asString,
//                        p.asJsonObject.get("productType").asString,
//                        p.asJsonObject.get("braTypes").asString,
//                        p.asJsonObject.get("categories").asString,
//                        p.asJsonObject.get("images").asString
//                        val price: Double? = null,
//                        val description: String? = null,
//                        val discount: Int? = null,
//                        val shop: Shop? = null,
//                        val productType: ProductType? = null,
//                        val braTypes: List<BraType> = ArrayList(),
//                        val categories: List<Category> = ArrayList(),
//                        val images: List<Image> = ArrayList()
