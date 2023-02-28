package com.insane.whatsthebra

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonArray
import com.insane.interfaces.ApiInterface
import com.insane.utils.Tools
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutCategoryButtons = findViewById<LinearLayout>(R.id.linearLayoutCategoryButtons)
        val scrollViewCategoryButtons = findViewById<HorizontalScrollView>(R.id.scrollViewCategoryButtons)
        val apiService = ApiInterface.create()
        val categories: Call<JsonArray> = apiService.fetchCategories()

        // REMOVE HORIZONTAL SCROLLBAR FROM VIEW CATEGORY BUTTONS
        scrollViewCategoryButtons.isHorizontalScrollBarEnabled = false

        categories.enqueue(object : retrofit2.Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val response = response.body()
                if (response != null) {
                    linearLayoutCategoryButtons.removeAllViews()
                    for (category in response) {
                        createButtonCategory(linearLayoutCategoryButtons, category.asJsonObject.get("name").asString)
                    }
                }
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.message?.let { Log.e("API", it) }
            }
        })

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createButtonCategory(view: LinearLayout, text: String) {
        val button = Button(this)
        // Layout Params
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(0,0,Tools.Window.dpToPx(10),0)
        button.layoutParams = params
        button.background = view.context.resources.getDrawable(R.drawable.ic_box_button_off, this.theme)
        button.typeface = Typeface.DEFAULT_BOLD
        button.setPadding(Tools.Window.dpToPx(20),0,Tools.Window.dpToPx(20),0)
        button.text = text
        button.setTextColor(this.resources.getColor(R.color.white, this.theme))
        // Add view
        view.addView(button)
    }

}