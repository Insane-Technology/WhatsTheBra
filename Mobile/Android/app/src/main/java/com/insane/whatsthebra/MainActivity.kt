package com.insane.whatsthebra

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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
        val constraintLayoutFilter = findViewById<ConstraintLayout>(R.id.constraintLayoutFilterModal)
        val imageButtonFilter = findViewById<ImageButton>(R.id.ib_filter)
        val frameLayoutBackground = findViewById<FrameLayout>(R.id.frameLayoutFilterBackground)
        val linearLayoutCheckboxContainerLeft = findViewById<LinearLayout>(R.id.linearLayoutCheckboxContainerLeft)
        val linearLayoutCheckboxContainerRight = findViewById<LinearLayout>(R.id.linearLayoutCheckboxContainerRight)
        val apiService = ApiInterface.create()
        val categories: Call<JsonArray> = apiService.fetchCategories()
        val braTypeList: Call<JsonArray> = apiService.fetchBraType()

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
                t.message?.let {
                    // TODO Trabalhar em caso de falha da api
                    Log.e("API", it)
                }
            }
        })

        braTypeList.enqueue(object : retrofit2.Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val response = response.body()
                if (response != null) {
                    linearLayoutCheckboxContainerLeft.removeAllViews()
                    linearLayoutCheckboxContainerRight.removeAllViews()
                    for ((index, braType) in response.withIndex()) {
                        Log.d("INFO", braType.toString())
                        if (index % 2 == 0) {
                            createCheckboxFilter(linearLayoutCheckboxContainerLeft,braType.asJsonObject.get("name").asString)
                        } else {
                            createCheckboxFilter(linearLayoutCheckboxContainerRight, braType.asJsonObject.get("name").asString)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.message?.let {
                    // TODO Trabalhar em caso de falha da api
                    Log.e("API", it)
                }
            }
        })

        imageButtonFilter.setOnClickListener(View.OnClickListener {
            constraintLayoutFilter.visibility = View.VISIBLE
        })

        frameLayoutBackground.setOnClickListener(View.OnClickListener {
            constraintLayoutFilter.visibility = View.GONE
        })

    }

    /**
     * Method to create category button
     */
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
        button.isAllCaps = false
        button.textSize = 15.00f
        button.setTextColor(this.resources.getColor(R.color.white, this.theme))
        // Add view
        view.addView(button)
    }

    private fun createCheckboxFilter(view: LinearLayout, text: String) {
        val checkBox = CheckBox(this)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        checkBox.layoutParams = params
        checkBox.text = text
        view.addView(checkBox)
    }

}