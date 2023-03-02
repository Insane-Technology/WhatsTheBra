package com.insane.whatsthebra.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.insane.whatsthebra.component.Component
import com.insane.whatsthebra.service.MainService
import com.insane.whatsthebra.R
import com.insane.whatsthebra.utils.Tools


class MainActivity : AppCompatActivity() {

    private val mainActivityComponent = Component(this)

    override fun onBackPressed() {
        // Avoid going back to SplashScreen activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearCategoryButtons: LinearLayout = findViewById(R.id.linearLayoutCategoryButtons)
        val constraintFilter: ConstraintLayout = findViewById(R.id.constraintLayoutFilterModal)
        val imgBtnFilter: ImageButton = findViewById(R.id.ib_filter)
        val frameBackground: FrameLayout = findViewById(R.id.frameLayoutFilterBackground)
        val linearCheckboxContainerLeft: LinearLayout = findViewById(R.id.linearLayoutCheckboxContainerLeft)
        val linearCheckboxContainerRight: LinearLayout = findViewById(R.id.linearLayoutCheckboxContainerRight)
        val linearFilterContainer: LinearLayout = findViewById(R.id.linearLayoutFilterContainer)
        val linearProductContainerLeft: LinearLayout = findViewById(R.id.linearLayoutProductContainerLeft)
        val linearProductContainerRight: LinearLayout = findViewById(R.id.linearLayoutProductContainerRight)
        val buttonFilterCancel: Button = findViewById(R.id.btn_filter_cancel)


        /*  ************************** ADD VIEWS *************************** */
        loadCategoryButtons(linearCategoryButtons)
        loadFilterList(linearCheckboxContainerLeft,linearCheckboxContainerRight)
        loadProducts(linearProductContainerLeft,linearProductContainerRight)
        /*  ************************ END ADD VIEWS ************************* */


        /* ************************ CLICK LISTENERS ************************ */
        // BUTTON FILTER
        imgBtnFilter.setOnClickListener(View.OnClickListener {
            constraintFilter.visibility = View.VISIBLE
        })
        // DARK BACKGROUND MODAL
        frameBackground.setOnClickListener(View.OnClickListener {
            constraintFilter.visibility = View.GONE
        })
        // BUTTON CANCEL FILTER MODAL
        buttonFilterCancel.setOnClickListener(View.OnClickListener {
            constraintFilter.visibility = View.GONE
        })
        // MODAL CONTAINER TO AVOID CLOSE MODAL
        linearFilterContainer.setOnClickListener(View.OnClickListener {})
    /* ********************** END CLICK LISTENERS ********************** */
    }

    private fun loadProducts(leftColumn: LinearLayout, rightColumn: LinearLayout) {
        leftColumn.removeAllViews()
        rightColumn.removeAllViews()
        for ((index, product) in MainService.getProducts().withIndex()) {
            if (index % 2 == 0) {
                mainActivityComponent.createProductContainer(leftColumn, product)
            } else {
                mainActivityComponent.createProductContainer(rightColumn, product)
            }
        }
    }

    private fun loadCategoryButtons(buttonsContainer: LinearLayout) {
        buttonsContainer.removeAllViews()
        for (category in MainService.getCategories()) {
            category.name?.let { mainActivityComponent.createButtonCategory(buttonsContainer, it) }
        }
    }

    private fun loadFilterList(leftColumn: LinearLayout, rightColumn: LinearLayout) {
        leftColumn.removeAllViews()
        rightColumn.removeAllViews()
        for ((index, braType) in MainService.getBraTypes().withIndex()) {
            if (index % 2 == 0) {
                braType.name?.let { mainActivityComponent.createCheckboxFilter(leftColumn, it) }
            } else {
                braType.name?.let { mainActivityComponent.createCheckboxFilter(rightColumn, it) }
            }
        }
    }

}