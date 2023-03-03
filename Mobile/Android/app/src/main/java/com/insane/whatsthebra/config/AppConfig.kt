package com.insane.whatsthebra.config

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.insane.whatsthebra.R
import com.insane.whatsthebra.utils.Tools

object AppConfig {

    /**
     * API base - host and end-points
     */
    object API {

        private const val apiHost: String = "http://160.238.220.112:9090/api/wtb-v1/"
        private const val imageEndPointId: String = "image/id/"
        private const val imageEndPoint: String = "image/"

        fun getHost(): String {
            return apiHost;
        }

        fun getImageUrl(imageId: Int): String {
            return "${apiHost}${imageEndPointId}${imageId}"
        }

        fun getImageUrl(imageName: String): String {
            return "${apiHost}${imageEndPoint}${imageName}"
        }

    }

    /**
     * APP Image configurations such as loader, thumbnail imageview template and thumbnail height
     */
    object Image {

        private const val thumbnailHeightDp = 250

        fun getThumbnailHeight(): Int {
            return Tools.Window.dpToPx(thumbnailHeightDp)
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

    }
}