package com.insane.service

import android.util.Log
import com.google.gson.JsonArray
import com.insane.interfaces.ApiInterface
import com.insane.model.BraType
import com.insane.model.Category
import retrofit2.Call
import retrofit2.Response

object MainService {

    private val apiService = ApiInterface.create()
    private var dataCallBack: DataCallBack? = null
    private var categoryTries = 0
    private var braTypeTries = 0
    private var categories = ArrayList<Category>()
    private var braTypes = ArrayList<BraType>()

    /**
     * Method to load data from API
     */
    fun loadData(dataCallBack: DataCallBack) {
        setDataCallBack(dataCallBack)
        loadCategories()
    }

    /**
     * Method to return a category list
     */
    private fun loadCategories() {
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
     * Method to return a bra type list
     */
    private fun loadBraTypeList() {
        val jsonBraTypeList: Call<JsonArray> = apiService.fetchBraType()
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
     * Setter to set up the Interface DataCallBack
     * @param dataCallBack it's an interface type DataCallBack
     */
    private fun setDataCallBack(dataCallBack: DataCallBack) {
        this.dataCallBack = dataCallBack
    }

    /**
     * Method called when service is done
     */
    private fun onServiceDone() {
        if (categories.size == 0 && categoryTries < 3) {
            categoryTries++
            loadCategories()
        } else if (braTypes.size == 0 && braTypeTries <= 3) {
            braTypeTries++
            loadBraTypeList()
        } else {
            dataCallBack!!.onDataLoaded()
        }
    }

    /**
     * Interface to implement a call back
     */
    interface DataCallBack {
        fun onDataLoaded()
    }

    fun getCategories(): ArrayList<Category> {
        return this.categories
    }

    fun getBraTypes(): ArrayList<BraType> {
        return this.braTypes
    }

}