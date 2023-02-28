package com.insane.whatsthebra

import android.annotation.SuppressLint
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.insane.utils.Tools

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutCategoryButtons = findViewById<LinearLayout>(R.id.linearLayoutCategoryButtons)
        val scrollViewCategoryButtons = findViewById<HorizontalScrollView>(R.id.scrollViewCategoryButtons)

        scrollViewCategoryButtons.isHorizontalScrollBarEnabled = false

        createButtonCategory(linearLayoutCategoryButtons, "Vestidos")
        createButtonCategory(linearLayoutCategoryButtons, "Blusas")
        createButtonCategory(linearLayoutCategoryButtons, "Sutiãs")
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createButtonCategory(view: LinearLayout, text: String) {

        val button = Button(this)

        // Layout Params
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(0,0,Tools.Window.dpToPx(10),0)

        button.layoutParams = params
        button.background = view.context.resources.getDrawable(R.drawable.ic_box_color, this.theme)
        button.typeface = Typeface.DEFAULT_BOLD
        button.text = text
        button.setTextColor(this.resources.getColor(R.color.white, this.theme))

        // Add view
        view.addView(button)

    }

}