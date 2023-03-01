package com.insane.whatsthebra

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.insane.component.Component
import com.insane.service.MainService


class MainActivity : AppCompatActivity() {

    private val component = Component(this)

    override fun onBackPressed() {
        // Avoid going back to SplashScreen activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutCategoryButtons: LinearLayout = findViewById<LinearLayout>(R.id.linearLayoutCategoryButtons)
        val scrollViewCategoryButtons: HorizontalScrollView = findViewById<HorizontalScrollView>(R.id.scrollViewCategoryButtons)
        val constraintLayoutFilter: ConstraintLayout = findViewById<ConstraintLayout>(R.id.constraintLayoutFilterModal)
        val imageButtonFilter: ImageButton = findViewById<ImageButton>(R.id.ib_filter)
        val frameLayoutBackground: FrameLayout = findViewById<FrameLayout>(R.id.frameLayoutFilterBackground)
        val linearLayoutCheckboxContainerLeft: LinearLayout = findViewById<LinearLayout>(R.id.linearLayoutCheckboxContainerLeft)
        val linearLayoutCheckboxContainerRight: LinearLayout = findViewById<LinearLayout>(R.id.linearLayoutCheckboxContainerRight)
        val linearLayoutFilterContainer: LinearLayout = findViewById<LinearLayout>(R.id.linearLayoutFilterContainer)
        val buttonFilterCancel: Button = findViewById<Button>(R.id.btn_filter_cancel)

        // REMOVE HORIZONTAL SCROLLBAR FROM VIEW CATEGORY BUTTONS
        scrollViewCategoryButtons.isHorizontalScrollBarEnabled = false

        // ADD VIEWS
        addCategoryButtons(linearLayoutCategoryButtons)
        addFilterCheckBoxList(linearLayoutCheckboxContainerLeft,linearLayoutCheckboxContainerRight)

        /* ************************ CLICK LISTENERS ************************ */
        // BUTTON FILTER
        imageButtonFilter.setOnClickListener(View.OnClickListener {
            constraintLayoutFilter.visibility = View.VISIBLE
        })
        // DARK BACKGROUND MODAL
        frameLayoutBackground.setOnClickListener(View.OnClickListener {
            constraintLayoutFilter.visibility = View.GONE
        })
        // BUTTON CANCEL FILTER MODAL
        buttonFilterCancel.setOnClickListener(View.OnClickListener {
            constraintLayoutFilter.visibility = View.GONE
        })
        // MODAL CONTAINER TO AVOID CLOSE MODAL
        linearLayoutFilterContainer.setOnClickListener(View.OnClickListener {})
        /* ********************** END CLICK LISTENERS ********************** */
    }

    private fun addCategoryButtons(buttonsContainer: LinearLayout) {
        buttonsContainer.removeAllViews()
        for (category in MainService.getCategories()) {
            category.name?.let { component.createButtonCategory(buttonsContainer, it) }
        }
    }

    private fun addFilterCheckBoxList(leftColumn: LinearLayout, rightColumn: LinearLayout) {
        leftColumn.removeAllViews()
        rightColumn.removeAllViews()
        for ((index, braType) in MainService.getBraTypes().withIndex()) {
            Log.d("INFO", braType.toString())
            if (index % 2 == 0) {
                braType.name?.let { component.createCheckboxFilter(leftColumn, it) }
            } else {
                braType.name?.let { component.createCheckboxFilter(rightColumn, it) }
            }
        }
    }

}