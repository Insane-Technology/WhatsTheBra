package com.insane.component

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Build
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.widget.CompoundButtonCompat
import com.insane.model.Product
import com.insane.utils.Tools
import com.insane.whatsthebra.R

class Component(private var activity: Activity) {

    /**
     * Method to create category button
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    fun createButtonCategory(view: LinearLayout, text: String) {
        val button = Button(activity)
        // Layout Params
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(0,0, Tools.Window.dpToPx(10),0)
        button.layoutParams = params
        button.background = view.context.resources.getDrawable(R.drawable.ic_box_button_off, activity.theme)
        button.typeface = Typeface.DEFAULT_BOLD
        button.setPadding(Tools.Window.dpToPx(20),0, Tools.Window.dpToPx(20),0)
        button.text = text
        button.isAllCaps = false
        button.textSize = 15.00f
        button.setTextColor(activity.resources.getColor(R.color.white, activity.theme))
        // Add view
        view.addView(button)
    }

    /**
     * Method to create a CheckBox
     */
    fun createCheckboxFilter(view: LinearLayout, text: String) {
        val checkBox = CheckBox(activity)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        checkBox.buttonTintList = ColorStateList.valueOf(activity.resources.getColor(R.color.gray_200, activity.theme))
        checkBox.layoutParams = params
        checkBox.text = text
        checkBox.setTextColor(activity.resources.getColor(R.color.gray_700, activity.theme))
        view.addView(checkBox)
    }

    /**
     * Method to create a Product container with image and labels
     */
    fun createProductContainer(view: LinearLayout, product: Product) {

    }

}