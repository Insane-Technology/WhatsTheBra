package com.insane.whatsthebra.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.Uri
import android.os.*
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.insane.whatsthebra.R
import com.insane.whatsthebra.model.Category
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.text.DecimalFormat
import java.text.Normalizer
import java.util.regex.Pattern
import kotlin.math.roundToInt


object Tools {

    private var doubleClick = 0

    object Connection {
        fun internetStatus(context: Context): Boolean {

            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
            // TODO: Verify if there is any other type of Connectivity Service other than WIFI, MOBILE and ETHERNET
            // TODO: VERIFY DEPRECATION
        }

    }

    object Text {
        fun removeAccents(string: String): String {
            var st = string
            st = Normalizer.normalize(string, Normalizer.Form.NFD)
            st = st.replace("[^\\p{ASCII}]".toRegex(), "")
            return Normalizer.normalize(st, Normalizer.Form.NFD)
                .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
        }

        fun isEmail(email: String?): Boolean {
            val emailRegex =
                "^[a-zA-Z\\d_+&*-]+(?:\\.[a-zA-Z\\d_+&*-]+)*@(?:[a-zA-Z\\d-]+\\.)+[a-zA-Z]{2,7}$"
            val pat = Pattern.compile(emailRegex)
            return if (email == null) false else pat.matcher(email).matches()
        }
    }

    object Currency {
        fun toReal(value: Double): String {
            val decimal = DecimalFormat("#,###.00")
            return "R$ ${decimal.format(value)}"
        }
    }

    object Encryption {
        fun sha256(base: String): String {
            return try {
                val digest = MessageDigest.getInstance("SHA-256")
                val hash = digest.digest(base.toByteArray(StandardCharsets.UTF_8))
                val hexString = StringBuilder()
                for (b in hash) {
                    val hex = Integer.toHexString(0xff and b.toInt())
                    if (hex.length == 1) hexString.append('0')
                    hexString.append(hex)
                }
                hexString.toString()
            } catch (ex: Exception) {
                throw RuntimeException(ex)
            }
        }
    }

    object Window {
        /**
         * Gets the window size either width or height
         * @param activity context
         * @param widthOrHeight parameter type String needs to be width or height
         * @return a Integer number
         */
        fun size(activity: Activity, widthOrHeight: String): Int {
            val dm = DisplayMetrics()
            activity.window.windowManager.defaultDisplay.getMetrics(dm)
            var result = 0
            if (widthOrHeight == "width") {
                result = dm.widthPixels
            } else if (widthOrHeight == "height") {
                result = dm.heightPixels
            }
            return result
        }

        fun dpToPx(dp: Int): Int {
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }

        fun spToPx(sp: Int): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                sp.toFloat(),
                Resources.getSystem().displayMetrics
            )
        }

        fun pxToDp(px: Int): Int {
            return (px / Resources.getSystem().displayMetrics.density).toInt()
        }

        fun convertDpToPixel(dp: Float): Int {
            val metrics = Resources.getSystem().displayMetrics
            val px = dp * (metrics.densityDpi / 160f)
            return px.roundToInt()
        }
    }

    object Device {
        fun vibrate(context: Context) {
            val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            // Vibrate for 500 milliseconds
            v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        }

        fun currentVersion(): String {
            val release =
                Build.VERSION.RELEASE.replace("(\\d+[.]\\d+)(.*)".toRegex(), "$1").toDouble()
            var codeName = "Unsupported" //below Jelly bean OR above Oreo
            if (release >= 4.1 && release < 4.4) codeName =
                "Jelly Bean" else if (release < 5) codeName =
                "Kit Kat" else if (release < 6) codeName =
                "Lollipop" else if (release < 7) codeName =
                "Marshmallow" else if (release < 8) codeName =
                "Nougat" else if (release < 9) codeName = "Oreo" else if (release < 10) codeName =
                "Android Q" else if (release >= 10) codeName = "Android 10"
            return codeName + " v" + release + ", API Level: " + Build.VERSION.SDK_INT
        }

        val isDoubleClick: Boolean
            get() {
                 doubleClick++
                 Handler(Looper.getMainLooper()).postDelayed({
                     doubleClick = 0
                 }, 500)
                return if (doubleClick >= 2) {
                    doubleClick = 0
                    true
                } else {
                    false
                }
            }
    }

    object Web {
        fun openBrowser(context: Context, url: String) {
            // OPEN BROWSER
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(browserIntent)
        }
    }

    object Show {
        fun noConnection(context: Context) {
            Toast.makeText(context, context.getString(R.string.noConnection), Toast.LENGTH_SHORT).show()
        }

        fun message(context: Context?, message: String?) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    object Component {
        private const val BASE_ID_BUTTON_CATEGORY = 1000
        private const val RECOMMENDATION_WIDTH = 100

        fun getLoader(context: Context): CircularProgressDrawable {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 15f
            circularProgressDrawable.centerRadius = 60f
            circularProgressDrawable.alpha = 50
            circularProgressDrawable.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(R.color.pink_700, BlendModeCompat.SRC_ATOP)
            circularProgressDrawable.start()
            return  circularProgressDrawable
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        fun getRecommendationImageView(context: Context): ImageView {
            val imageView = ImageView(context)
            val paramsProductImage = LinearLayout.LayoutParams(Window.dpToPx(RECOMMENDATION_WIDTH), LinearLayout.LayoutParams.MATCH_PARENT)
            paramsProductImage.setMargins(0,0,Window.dpToPx(8),0)
            imageView.layoutParams = paramsProductImage
            imageView.setBackgroundColor(context.getColor(R.color.pink_200))
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.elevation = 0F
            return imageView
        }

        fun getButtonCategoryId(category: Category): Int {
            return BASE_ID_BUTTON_CATEGORY+category.id
        }

    }

}