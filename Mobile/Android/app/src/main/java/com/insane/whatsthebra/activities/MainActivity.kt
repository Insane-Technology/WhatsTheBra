package com.insane.whatsthebra.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.insane.whatsthebra.R
import com.insane.whatsthebra.component.MainComponent
import com.insane.whatsthebra.model.BraType
import com.insane.whatsthebra.model.Category
import com.insane.whatsthebra.model.Product
import com.insane.whatsthebra.service.MainService
import com.insane.whatsthebra.utils.Tools


class MainActivity : AppCompatActivity() {

    private val mainActivityComponent = MainComponent(this)
    private var selectedFilters: ArrayList<BraType> = ArrayList()
    private var selectedCategory: Category = MainService.getCategories()[0]

    override fun onBackPressed() {
        // Avoid going back to SplashScreen activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // CATEGORY CONTAINER
        val linearCategoryButtons: LinearLayout = findViewById(R.id.linearLayoutCategoryButtons)

        // FILTER CONTAINERS
        val linearFilterContainer: LinearLayout = findViewById(R.id.linearLayoutFilterContainer)
        val frameBackground: FrameLayout = findViewById(R.id.frameLayoutFilterBackground)
        val linearCheckboxContainerLeft: LinearLayout = findViewById(R.id.linearLayoutCheckboxContainerLeft)
        val linearCheckboxContainerRight: LinearLayout = findViewById(R.id.linearLayoutCheckboxContainerRight)
        val constraintFilter: ConstraintLayout = findViewById(R.id.constraintLayoutFilterModal)

        // FILTER BUTTONS
        val imgBtnFilter: ImageButton = findViewById(R.id.ib_filter)
        val buttonFilterApply: Button = findViewById(R.id.btn_filter_apply)
        val buttonFilterCancel: Button = findViewById(R.id.btn_filter_cancel)

        // BOTTOM MENU BAR BUTTONS
        val btnHome: ImageView = findViewById(R.id.iv_home)
        val btnFavourite: ImageView = findViewById(R.id.iv_heart)
        val btnNotification: ImageView = findViewById(R.id.iv_notification)
        val btnProfile: ImageView = findViewById(R.id.iv_profile)


        /*  ************************** ADD VIEWS *************************** */
        loadCategoryButtons(linearCategoryButtons)
        loadFilters(linearCheckboxContainerLeft,linearCheckboxContainerRight)
        setCategoryOn(selectedCategory)
        /*  ************************ END ADD VIEWS ************************* */


        /* ************************ CLICK LISTENERS ************************ */
        // BUTTON FILTER
        imgBtnFilter.setOnClickListener { openModalFilter(constraintFilter) }
        // DARK BACKGROUND MODAL
        frameBackground.setOnClickListener { closeModalFilter(constraintFilter) }
        // BUTTON CANCEL FILTER MODAL
        buttonFilterCancel.setOnClickListener { closeModalFilter(constraintFilter) }
        // BUTTON APPLY FILTER MODAL
        buttonFilterApply.setOnClickListener {
            closeModalFilter(constraintFilter)
            loadProducts()
        }
        // MODAL CONTAINER TO AVOID CLOSE MODAL ON MODAL's CLICK
        linearFilterContainer.setOnClickListener { /* Ignore event */ }
        /* ********************** END CLICK LISTENERS ********************** */
    }

    private fun openModalFilter(container: ConstraintLayout) {
        container.visibility = View.VISIBLE
    }

    private fun closeModalFilter(container: ConstraintLayout) {
        container.visibility = View.GONE
    }

    fun addFilter(braType: BraType) {
        selectedFilters.add(braType)
    }

    fun removeFilter(braType: BraType) {
        selectedFilters.remove(braType)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setCategoryOn(category: Category) {
        // Unselect selected button
        val oldSelectedButton: Button = this.findViewById(selectedCategory.id)
        oldSelectedButton.background = this.resources.getDrawable(R.drawable.ic_box_button_off, this.theme)

        // Set new category selected
        selectedCategory = category

        // Select Button
        val selectedButton: Button = this.findViewById(category.id)
        selectedButton.background = this.resources.getDrawable(R.drawable.ic_box_button_on, this.theme)

        // Reload Products
        loadProducts()
    }

    fun openProductDetails(product: Product) {
        Tools.Show.message(this, "Openning Product with id ${product.id}")
        // TODO Implement click listener to open product details page
    }

    fun setFavouriteProduct(product: Product) {
        Tools.Show.message(this, "Set product with id ${product.id} as favourite!")
        // TODO Implement click listener to set product as favourite
    }

    private fun checkCategoryProduct(product: Product): Boolean {
        if (selectedCategory.id <= 0) return true
        if (product.categories.any { it == selectedCategory})
            return true
        return false
    }

    private fun checkFilterProduct(product: Product, filters: ArrayList<BraType>): Boolean {
        if (filters.size <= 0) return true
        else {
            for (filter in filters) {
                if (product.braTypes.any { it == filter }) return true
            }
        }
        return false
    }

    private fun loadProducts() {
        val leftColumn: LinearLayout = findViewById(R.id.linearLayoutProductContainerLeft)
        val rightColumn: LinearLayout = findViewById(R.id.linearLayoutProductContainerRight)
        var productMatches = 0

        // CLEAR VIEWS
        leftColumn.removeAllViews()
        rightColumn.removeAllViews()

        for (product in MainService.getProducts()) {
            if (checkCategoryProduct(product)) {
                if (checkFilterProduct(product, selectedFilters)) {
                    productMatches++
                    val view: View = mainActivityComponent.createProductContainer(product)
                    // ADD VIEW TO EACH COLUMN
                    if (productMatches % 2 == 0) {
                        rightColumn.addView(view)
                    } else {
                        leftColumn.addView(view)
                    }
                }
            }
        }
    }

    private fun loadCategoryButtons(buttonsContainer: LinearLayout) {
        buttonsContainer.removeAllViews()
        for (category in MainService.getCategories()) {
            mainActivityComponent.createButtonCategory(buttonsContainer, category)
        }
    }

    private fun loadFilters(leftColumn: LinearLayout, rightColumn: LinearLayout) {
        leftColumn.removeAllViews()
        rightColumn.removeAllViews()
        for ((index, braType) in MainService.getBraTypes().withIndex()) {
            if (index % 2 == 0) {
                mainActivityComponent.createCheckboxFilter(leftColumn, braType)
            } else {
                mainActivityComponent.createCheckboxFilter(rightColumn, braType)
            }
        }
    }

}