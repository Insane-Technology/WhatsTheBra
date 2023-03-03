package com.insane.whatsthebra.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.insane.whatsthebra.R
import com.insane.whatsthebra.activities.MainActivity
import com.insane.whatsthebra.config.AppConfig
import com.insane.whatsthebra.config.GlideApp
import com.insane.whatsthebra.model.Product

class MainImageAdapter(private var context: MainActivity, private var product: Product) : PagerAdapter() {

    override fun getCount(): Int {
        return product.images.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        // IMAGE VIEW
        val imageView = AppConfig.Image.getImageViewThumbnailTemplate(context)

        // RETRIEVING IMAGES FROM SERVER WITH GLIDE
        GlideApp.with(context)
            .load(AppConfig.API.getImageUrl(product.images[position].id))
            .error(context.resources.getDrawable(R.drawable.ic_broken_image, context.theme))
            .fallback(context.resources.getDrawable(R.drawable.ic_broken_image, context.theme))
            .placeholder(AppConfig.Image.getLoader(context))
            .centerCrop()
            .into(imageView)
        container.addView(imageView, 0)

        // CLICK LISTENER
        imageView.setOnClickListener { context.openProductDetails(product) }

        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as ImageView)
    }

}