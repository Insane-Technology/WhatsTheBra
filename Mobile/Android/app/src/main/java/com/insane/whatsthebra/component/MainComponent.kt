package com.insane.whatsthebra.component

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.text.LineBreaker
import android.os.Build
import android.view.Gravity
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.insane.whatsthebra.R
import com.insane.whatsthebra.activity.MainActivity
import com.insane.whatsthebra.adapter.MainImageAdapter
import com.insane.whatsthebra.config.AppConfig
import com.insane.whatsthebra.model.BraType
import com.insane.whatsthebra.model.Category
import com.insane.whatsthebra.model.Product
import com.insane.whatsthebra.service.UserService
import com.insane.whatsthebra.utils.Tools
import java.text.DecimalFormat


class MainComponent(private val context: MainActivity) {

    /**
     * Method to create category button
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    fun createButtonCategory(view: LinearLayout, category: Category) {
        val button = Button(context)
        // Layout Params
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(0,0, Tools.Window.dpToPx(10),0)
        button.id = AppConfig.Component.getButtonCategoryId(category)
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
     * Method to create a CheckBox
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
     * Method to create a Product container with image and labels
     */
    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    fun createProductContainer(product: Product): LinearLayout {
        // MAIN LINEAR CONTAINER VERTICAL
        val mainLinearContainer = LinearLayout(context)
        val paramsContainer = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        paramsContainer.setMargins(0,Tools.Window.dpToPx(10),0,Tools.Window.dpToPx(10))
        mainLinearContainer.layoutParams = paramsContainer
        mainLinearContainer.orientation = LinearLayout.VERTICAL

        // CARD VIEW
        val cardView = CardView(context)
        val paramsCardView = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        cardView.layoutParams = paramsCardView
        cardView.elevation = 0F
        cardView.radius = Tools.Window.dpToPx(20).toFloat()

        // PRODUCT IMAGES WITH VIEWPAGER OR PRODUCT IMAGE PLACEHOLDER
        val productImages = ViewPager(context)
        val productImage = AppConfig.Image.getImageViewThumbnailTemplate(context)
        if (product.images.size > 0) {
            productImages.layoutParams = LinearLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, AppConfig.Image.getThumbnailHeight())
            productImages.adapter = MainImageAdapter(context, product)
            productImages.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(position: Int,positionOffset: Float,positionOffsetPixels: Int) { /* scrollDisplay.requestDisallowInterceptTouchEvent(true); */ }
                override fun onPageSelected(position: Int) {}
                override fun onPageScrollStateChanged(state: Int) { /* scrollDisplay.requestDisallowInterceptTouchEvent(false); */ }
            })
        } else {
            productImage.setImageResource(R.drawable.ic_broken_image)
        }

        // HEART IMAGE
        val heartImage = ImageView(context)
        heartImage.id = AppConfig.Component.getImageViewFavouriteId(product)
        val paramsHeartImage = LinearLayout.LayoutParams(Tools.Window.dpToPx(30), LinearLayout.LayoutParams.WRAP_CONTENT)
        paramsHeartImage.setMargins(Tools.Window.dpToPx(8),Tools.Window.dpToPx(2),0,0)
        heartImage.layoutParams = paramsHeartImage
        heartImage.elevation = Tools.Window.dpToPx(10).toFloat()
        if (UserService.user.favouriteProducts.any { it == product })
            heartImage.setImageDrawable(context.resources.getDrawable(R.drawable.ic_heart_color, context.theme))
        else
            heartImage.setImageDrawable(context.resources.getDrawable(R.drawable.ic_heart, context.theme))

        // INNER LINEAR LABEL AND PRICE CONTAINER
        val innerLinearContainer = LinearLayout(context)
        val paramsInnerContainer = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        paramsInnerContainer.setMargins(0,Tools.Window.dpToPx(5),0,0)
        paramsInnerContainer.weight = 1F
        innerLinearContainer.layoutParams = paramsInnerContainer
        innerLinearContainer.orientation = LinearLayout.VERTICAL

        // PRODUCT DESCRIPTION TEXT
        val productDescriptionTextView = TextView(context)
        val paramsProductDescription = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        productDescriptionTextView.layoutParams = paramsProductDescription
        productDescriptionTextView.setTextColor(context.resources.getColor(R.color.gray_700, context.theme))
        // TODO Justification mode is applied only for greater or equal SDK|API Level 29
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { productDescriptionTextView.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD }
        productDescriptionTextView.gravity = Gravity.START
        productDescriptionTextView.text = product.description

        // LINEAR PRICE AND DISCOUNT CONTAINER
        val linearPriceContainer = LinearLayout(context)
        val paramsInnerPriceContainer = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        paramsInnerPriceContainer.setMargins(0,0,0,0)
        paramsInnerPriceContainer.weight = Tools.Window.dpToPx(1).toFloat()
        linearPriceContainer.layoutParams = paramsInnerPriceContainer
        linearPriceContainer.orientation = LinearLayout.HORIZONTAL

        // PRICE TEXT VIEW
        val tvPrice = TextView(context)
        val paramsTvPrice = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        paramsTvPrice.weight = 1.4F
        tvPrice.layoutParams = paramsTvPrice
        tvPrice.setTextColor(context.resources.getColor(R.color.gray_700, context.theme))
        tvPrice.textSize = 13F
        tvPrice.gravity = Gravity.START
        val fontFamily: Typeface = context.resources.getFont(R.font.roboto_black)
        tvPrice.typeface = fontFamily
        val dec = DecimalFormat("#,###.00")
        val priceAfterDiscount = product.price?.minus((product.price * product.discount!! /100))
        val price: String = dec.format(priceAfterDiscount)
        tvPrice.text = "R$ $price"
        // TODO Change the way to get the Reais symbol perhaps make a method in class to getPrice
        // TODO Ajustar o ponto e virgula no valor - pontos para mil e virgula para centavos

        // PRICE AND DISCOUNT VIEWS
        val tvOriginalPrice = TextView(context)
        val tvDiscount = TextView(context)
        if (product.discount!! > 0) {
            // ORIGINAL PRICE TEXT VIEW
            val paramsTvOriginalPrice =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
            paramsTvOriginalPrice.weight = 1.2F
            tvOriginalPrice.layoutParams = paramsTvOriginalPrice
            tvOriginalPrice.setTextColor(
                context.resources.getColor(
                    R.color.gray_500,
                    context.theme
                )
            )
            tvOriginalPrice.textSize = 11F
            tvOriginalPrice.gravity = Gravity.CENTER
            tvOriginalPrice.typeface = Typeface.DEFAULT_BOLD
            tvOriginalPrice.text = "R$ ${dec.format(product.price)}"
            tvOriginalPrice.paintFlags = tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            // TODO Change the way to get the Reais symbol perhaps make a method in class to getPrice
            // TODO Ajustar o ponto e virgula no valor - pontos para mil e virgula para centavos

            // ORIGINAL PRICE TEXT VIEW
            val paramsTvDiscount =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
            paramsTvDiscount.weight = 1F
            tvDiscount.layoutParams = paramsTvDiscount
            tvDiscount.setTextColor(context.resources.getColor(R.color.pink_700, context.theme))
            tvDiscount.textSize = 11F
            tvDiscount.gravity = Gravity.END
            tvDiscount.typeface = Typeface.DEFAULT_BOLD
            tvDiscount.text = "${product.discount}% OFF"
            // TODO Change the way to get the Reais symbol perhaps make a method in class to getPrice
        }

        // ADD VIEWS
        mainLinearContainer.addView(cardView)
        if (product.images.size > 0) cardView.addView(productImages) else cardView.addView(productImage)
        cardView.addView(heartImage)
        mainLinearContainer.addView(innerLinearContainer)
        innerLinearContainer.addView(productDescriptionTextView)
        innerLinearContainer.addView(linearPriceContainer)
        linearPriceContainer.addView(tvPrice)
        if (product.discount > 0) {
            linearPriceContainer.addView(tvOriginalPrice)
            linearPriceContainer.addView(tvDiscount)
        }

        // CLICK LISTENERS
        productImage.setOnClickListener { context.openProductDetails(product) }
        innerLinearContainer.setOnClickListener { context.openProductDetails(product) }
        heartImage.setOnClickListener { context.setFavouriteProduct(product) }

        // RETURN ALL VIEWS IN MAIN CONTAINER
        return mainLinearContainer
    }

}