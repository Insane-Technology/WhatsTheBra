package com.insane.whatsthebra.component

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.text.LineBreaker
import android.os.Build
import android.view.Gravity
import android.widget.*
import androidx.cardview.widget.CardView
import com.insane.whatsthebra.R
import com.insane.whatsthebra.model.Product
import com.insane.whatsthebra.utils.Tools
import java.text.DecimalFormat


class Component(private val activity: Activity) {

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
    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    fun createProductContainer(product: Product): LinearLayout {
        // MAIN LINEAR CONTAINER VERTICAL
        val mainLinearContainer = LinearLayout(activity)
        val paramsContainer = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        paramsContainer.setMargins(0,Tools.Window.dpToPx(10),0,Tools.Window.dpToPx(10))
        mainLinearContainer.layoutParams = paramsContainer
        mainLinearContainer.orientation = LinearLayout.VERTICAL

        // CARD VIEW
        val cardView = CardView(activity)
        val paramsCardView = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        cardView.layoutParams = paramsCardView
        cardView.elevation = Tools.Window.dpToPx(10).toFloat()
        cardView.radius = Tools.Window.dpToPx(20).toFloat()

        // PRODUCT IMAGE
        val productImage = ImageView(activity)
        val paramsProductImage = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Tools.Window.dpToPx(240))
        productImage.layoutParams = paramsProductImage
        productImage.setBackgroundColor(activity.resources.getColor(R.color.white, activity.theme))
        productImage.elevation = Tools.Window.dpToPx(1).toFloat()
        productImage.scaleType = ImageView.ScaleType.CENTER_CROP
        productImage.setImageDrawable(activity.resources.getDrawable(R.drawable.sutia1, activity.theme))
        // TODO Check the background because the corners are with shadow

        // HEART IMAGE
        val heartImage = ImageView(activity)
        val paramsHeartImage = LinearLayout.LayoutParams(Tools.Window.dpToPx(30), LinearLayout.LayoutParams.WRAP_CONTENT)
        paramsHeartImage.setMargins(Tools.Window.dpToPx(8),Tools.Window.dpToPx(2),0,0)
        heartImage.layoutParams = paramsHeartImage
        heartImage.elevation = Tools.Window.dpToPx(10).toFloat()
        heartImage.setImageDrawable(activity.resources.getDrawable(R.drawable.ic_heart, activity.theme))

        // INNER LINEAR LABEL AND PRICE CONTAINER
        val innerLinearContainer = LinearLayout(activity)
        val paramsInnerContainer = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        paramsInnerContainer.setMargins(0,Tools.Window.dpToPx(5),0,0)
        paramsInnerContainer.weight = 1F
        innerLinearContainer.layoutParams = paramsInnerContainer
        innerLinearContainer.orientation = LinearLayout.VERTICAL

        // PRODUCT DESCRIPTION TEXT
        val productDescriptionTextView = TextView(activity)
        val paramsProductDescription = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        productDescriptionTextView.layoutParams = paramsProductDescription
        productDescriptionTextView.setTextColor(activity.resources.getColor(R.color.gray_700, activity.theme))
        // TODO Justification mode is applied only for greater or equal SDK|API Level 29
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { productDescriptionTextView.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD }
        productDescriptionTextView.gravity = Gravity.START
        productDescriptionTextView.text = product.description

        // LINEAR PRICE AND DISCOUNT CONTAINER
        val linearPriceContainer = LinearLayout(activity)
        val paramsInnerPriceContainer = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        paramsInnerPriceContainer.setMargins(0,0,0,0)
        paramsInnerPriceContainer.weight = Tools.Window.dpToPx(1).toFloat()
        linearPriceContainer.layoutParams = paramsInnerPriceContainer
        linearPriceContainer.orientation = LinearLayout.HORIZONTAL

        // PRICE TEXT VIEW
        val tvPrice = TextView(activity)
        val paramsTvPrice = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        paramsTvPrice.weight = 1.4F
        tvPrice.layoutParams = paramsTvPrice
        tvPrice.setTextColor(activity.resources.getColor(R.color.gray_700, activity.theme))
        tvPrice.textSize = 13F
        tvPrice.gravity = Gravity.START
        val fontFamily: Typeface = activity.resources.getFont(R.font.roboto_black)
        tvPrice.typeface = fontFamily
        val dec = DecimalFormat("#,###.00")
        val priceAfterDiscount = product.price?.minus((product.price * product.discount!! /100))
        val price: String = dec.format(priceAfterDiscount)
        tvPrice.text = "R$ $price"
        // TODO Change the way to get the Reais symbol perhaps make a method in class to getPrice

        // PRICE AND DISCOUNT VIEWS
        val tvOriginalPrice = TextView(activity)
        val tvDiscount = TextView(activity)
        if (product.discount!! > 0) {
            // ORIGINAL PRICE TEXT VIEW
            val paramsTvOriginalPrice =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
            paramsTvOriginalPrice.weight = 1.2F
            tvOriginalPrice.layoutParams = paramsTvOriginalPrice
            tvOriginalPrice.setTextColor(
                activity.resources.getColor(
                    R.color.gray_500,
                    activity.theme
                )
            )
            tvOriginalPrice.textSize = 11F
            tvOriginalPrice.gravity = Gravity.CENTER
            tvOriginalPrice.typeface = Typeface.DEFAULT_BOLD
            tvOriginalPrice.text = "R$ ${dec.format(product.price)}"
            // TODO Change the way to get the Reais symbol perhaps make a method in class to getPrice

            // ORIGINAL PRICE TEXT VIEW
            val paramsTvDiscount =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
            paramsTvDiscount.weight = 1F
            tvDiscount.layoutParams = paramsTvDiscount
            tvDiscount.setTextColor(activity.resources.getColor(R.color.pink_700, activity.theme))
            tvDiscount.textSize = 11F
            tvDiscount.gravity = Gravity.END
            tvDiscount.typeface = Typeface.DEFAULT_BOLD
            tvDiscount.text = "${product.discount}% OFF"
            // TODO Change the way to get the Reais symbol perhaps make a method in class to getPrice
        }

        // ADD VIEWS
        mainLinearContainer.addView(cardView)
        cardView.addView(productImage)
        cardView.addView(heartImage)
        mainLinearContainer.addView(innerLinearContainer)
        innerLinearContainer.addView(productDescriptionTextView)
        innerLinearContainer.addView(linearPriceContainer)
        linearPriceContainer.addView(tvPrice)
        if (product.discount!! > 0) {
            linearPriceContainer.addView(tvOriginalPrice)
            linearPriceContainer.addView(tvDiscount)
        }

        // RETURN ALL VIEWS IN MAIN CONTAINER
        return mainLinearContainer
    }

}