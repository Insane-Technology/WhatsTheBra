package com.insane.whatsthebra.component

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.text.LineBreaker
import android.os.Build
import android.widget.*
import com.insane.whatsthebra.R
import com.insane.whatsthebra.activity.MainActivity
import com.insane.whatsthebra.model.BraType
import com.insane.whatsthebra.model.Category
import com.insane.whatsthebra.utils.Tools


class MainComponent(private val context: MainActivity) {

    /**
     * Method to create category button and add to the parent view to be displayed
     * @param view is the parent View type LinearLayout where the button will be added as child
     * @param category is an instance of the object Category that will be the button title and value
     *
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    fun createButtonCategory(view: LinearLayout, category: Category) {
        val button = Button(context)
        // Layout Params
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(0,0, Tools.Window.dpToPx(10),0)
        button.id = Tools.Component.getButtonCategoryId(category)
        button.layoutParams = params
        button.background = view.context.resources.getDrawable(R.drawable.ic_box_button_off, context.theme)
        button.typeface = Typeface.DEFAULT_BOLD
        button.setPadding(Tools.Window.dpToPx(20),0, Tools.Window.dpToPx(20),0)
        button.text = category.name
        button.isAllCaps = false
        button.textSize = 15.00f
        button.setTextColor(context.resources.getColor(R.color.white, context.theme))

        // Add view
        view.addView(button)

        // Click Listener
        button.setOnClickListener {
            context.setCategoryOn(category)
        }
    }

    /**
     * Method to create a CheckBox and add to the parent view
     * @param view is the parent View type LinearLayout where the checkbox will be added as child
     * @param braType is an instance of the object BraType that will be the checkbox title and value
     */
    fun createCheckboxFilter(view: LinearLayout, braType: BraType) {
        val checkBox = CheckBox(context)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        checkBox.buttonTintList = ColorStateList.valueOf(context.resources.getColor(R.color.gray_200, context.theme))
        checkBox.layoutParams = params
        checkBox.text = braType.name
        checkBox.setTextColor(context.resources.getColor(R.color.gray_700, context.theme))
        view.addView(checkBox)

        checkBox.setOnClickListener {
            if (checkBox.isChecked)
                context.addFilter(braType)
            else
                context.removeFilter(braType)
        }
    }

    /**
     * Method to create a TextView
     * @param text is a String parameter that will be displayed
     * @param gravity is an Int parameter to define the TextView Gravity constant, like Gravity.CENTER
     */
    fun createTextView(text: String, gravity: Int): TextView {
        // PRODUCT DESCRIPTION TEXT
        val textView = TextView(context)
        val paramsTextView = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        textView.layoutParams = paramsTextView
        textView.setPadding(Tools.Window.dpToPx(20),0,Tools.Window.dpToPx(20),0)
        textView.setTextColor(context.resources.getColor(R.color.gray_700, context.theme))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { textView.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD }
        textView.gravity = gravity
        textView.text = text
        return textView
    }

}