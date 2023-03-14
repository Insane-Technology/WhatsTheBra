package com.insane.whatsthebra.interfaces

import com.google.gson.JsonArray
import com.insane.whatsthebra.config.AppConfig
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface  {

    @GET("category")
    fun fetchCategories(): Call<JsonArray>

    @GET("bra-type")
    fun fetchBraTypes(): Call<JsonArray>

    @GET("shop")
    fun fetchShops(): Call<JsonArray>

    @GET("product-type")
    fun fetchProductTypes(): Call<JsonArray>

    @GET("product")
    fun fetchProducts(): Call<JsonArray>

    @GET("image")
    fun fetchImages(): Call<JsonArray>

    @GET("location/BR/{state}/city")
    fun fetchCityByState(@Path("state") state: String) : Call<JsonArray>

    companion object {
        fun create() : ApiInterface {
            val client = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppConfig.API.getHost())
                .build().create(ApiInterface::class.java)
        }
    }
}