package com.insane.whatsthebra.config

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.insane.whatsthebra.R
import com.insane.whatsthebra.model.Category
import com.insane.whatsthebra.model.Product
import com.insane.whatsthebra.utils.Tools

object AppConfig {

    /**
     * API base - host and end-points
     */
    object API {

        private const val hostBase: String = "https://insane-wtb.rcastrucci.com/"
        private const val api: String = "api/wtb-v1/"
        private const val imageEndPointId: String = "image/id/"
        private const val imageEndPoint: String = "image/"

        fun getHostBase(): String {
            return hostBase;
        }

        fun getHost(): String {
            return "${hostBase}${api}";
        }

        fun getImageUrl(imageId: Int): String {
            return "${hostBase}${api}${imageEndPointId}${imageId}"
        }

        fun getImageUrl(imageName: String): String {
            return "${hostBase}${api}${imageEndPoint}${imageName}"
        }

    }

    /**
     * APP Image configurations such as loader, thumbnail imageview template and thumbnail height
     */
    object Image {

        private const val thumbnailHeightDp = 250
        private const val recommendationWidthDp = 120

        fun getThumbnailHeight(): Int {
            return Tools.Window.dpToPx(thumbnailHeightDp)
        }

        fun getRecommendationWidth(): Int {
            return Tools.Window.dpToPx(recommendationWidthDp)
        }

        fun getLoader(context: Context): CircularProgressDrawable {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 15f
            circularProgressDrawable.centerRadius = 60f
            circularProgressDrawable.alpha = 50
            circularProgressDrawable.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(R.color.pink_700, BlendModeCompat.SRC_ATOP)
            circularProgressDrawable.start()
            return  circularProgressDrawable
        }

        fun getImageViewThumbnailTemplate(context: Context): ImageView {
            val imageView = ImageView(context)
            val paramsProductImage = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getThumbnailHeight())
            imageView.layoutParams = paramsProductImage
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setBackgroundColor(context.getColor(R.color.pink_200))
            imageView.elevation = 0F
            return imageView
        }

        fun getImageViewDetailTemplate(context: Context): ImageView {
            val imageView = ImageView(context)
            val paramsProductImage = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            imageView.layoutParams = paramsProductImage
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView.setBackgroundColor(context.getColor(R.color.pink_200))
            imageView.elevation = 0F
            return imageView
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        fun getImageViewRecommendationTemplate(context: Context): ImageView {
            val imageView = ImageView(context)
            val paramsProductImage = LinearLayout.LayoutParams(getRecommendationWidth(), LinearLayout.LayoutParams.MATCH_PARENT)
            paramsProductImage.setMargins(0,0,Tools.Window.dpToPx(8),0)
            imageView.layoutParams = paramsProductImage
            imageView.setBackgroundColor(context.getColor(R.color.pink_200))
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.elevation = 0F
            return imageView
        }

    }

    object Component {
        private const val BASE_ID_BUTTON_CATEGORY = 1000
        private const val BASE_ID_IMAGEVIEW_FAVOURITE = 2000

        fun getButtonCategoryId(category: Category): Int {
            return BASE_ID_BUTTON_CATEGORY+category.id
        }

        fun getImageViewFavouriteId(product: Product): Int {
            return BASE_ID_IMAGEVIEW_FAVOURITE+product.id
        }

    }

}