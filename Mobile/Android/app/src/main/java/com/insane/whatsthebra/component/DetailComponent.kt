package com.insane.whatsthebra.component

import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.insane.whatsthebra.fragment.DetailFragment
import com.insane.whatsthebra.model.Product
import com.insane.whatsthebra.utils.Tools

class DetailComponent(private val fragment: DetailFragment) {

    fun createDetailContainer(product: Product): LinearLayout {

        // MAIN LINEAR CONTAINER VERTICAL
        val mainLinearContainer = LinearLayout(fragment.context)
        val paramsContainer = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        paramsContainer.setMargins(0, Tools.Window.dpToPx(10),0, Tools.Window.dpToPx(10))
        mainLinearContainer.layoutParams = paramsContainer
        mainLinearContainer.orientation = LinearLayout.VERTICAL

        // CARD VIEW
        val cardView = fragment.context?.let { CardView(it) }
        val paramsCardView = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        cardView?.layoutParams = paramsCardView
        cardView?.elevation = 0F
        cardView?.radius = Tools.Window.dpToPx(20).toFloat()


        return mainLinearContainer
    }

}