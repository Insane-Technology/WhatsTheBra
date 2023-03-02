package com.insane.whatsthebra.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.insane.whatsthebra.R
import com.insane.whatsthebra.model.Image
import com.insane.whatsthebra.utils.Tools

class ImageAdapter(private var context: Context?, private var images: ArrayList<Image>?) : PagerAdapter() {

    var circularProgressDrawable: CircularProgressDrawable? = null

    override fun getCount(): Int {
        return images!!.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        // IMAGE VIEW
        val imageView = ImageView(context)
        val paramsProductImage = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Tools.Window.dpToPx(240))
        imageView.layoutParams = paramsProductImage
        imageView.setBackgroundColor(context!!.resources.getColor(R.color.white, context!!.theme))
        imageView.elevation = Tools.Window.dpToPx(1).toFloat()
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        // CREATE A CIRCULAR DRAWABLE IMAGE ANIMATED FOR A PLACEHOLDER \\
        circularProgressDrawable = CircularProgressDrawable(context!!)
        circularProgressDrawable!!.strokeWidth = 15f
        circularProgressDrawable!!.centerRadius = 60f
        circularProgressDrawable!!.alpha = 25
        circularProgressDrawable!!.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(R.color.pink_700, BlendModeCompat.LIGHTEN)
        circularProgressDrawable!!.start()

        // RETRIEVING IMAGES PATH ON SERVER
        val url =  "http://160.238.220.112:9090/api/wtb-v1/image/id/${images!![position].id}"
        Glide.with(context!!)
            .load(url)
            .error(context!!.resources.getDrawable(R.drawable.ic_logo, context!!.theme))
            .fallback(context!!.resources.getDrawable(R.drawable.ic_logo, context!!.theme))
            .placeholder(circularProgressDrawable)
            .centerCrop()
            .into(imageView)
        container.addView(imageView, 0)

        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView)
    }

}