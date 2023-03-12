package com.insane.whatsthebra.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.insane.whatsthebra.R
import com.insane.whatsthebra.component.MainComponent
import com.insane.whatsthebra.config.AppConfig
import com.insane.whatsthebra.interfaces.DataCallBack
import com.insane.whatsthebra.model.BraType
import com.insane.whatsthebra.model.Category
import com.insane.whatsthebra.model.Product
import com.insane.whatsthebra.service.MainService
import com.insane.whatsthebra.utils.Tools


class MainActivity : AppCompatActivity(), DataCallBack {

    private val mainActivityComponent = MainComponent(this)
    private var selectedFilters: ArrayList<BraType> = ArrayList()
    private var selectedCategory: Category = MainService.getCategories()[0]
    private var refreshLayout: SwipeRefreshLayout? = null
    private var isFavouriteList = false

    override fun onBackPressed() { /* Avoid going back to SplashScreen activity */ }

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

        // BOTTOM MENU BAR IMAGE VIEWS
        val ivHome: ImageView = findViewById(R.id.iv_home)
        val ivFavourite: ImageView = findViewById(R.id.iv_heart)
        val ivNotification: ImageView = findViewById(R.id.iv_notification)
        val ivProfile: ImageView = findViewById(R.id.iv_profile)

        // PULL REFRESH
        refreshLayout = findViewById(R.id.swipeRefreshLayout)

        /*  ************************** ADD VIEWS *************************** */
        loadCategoryButtons(linearCategoryButtons)
        loadFilters(linearCheckboxContainerLeft,linearCheckboxContainerRight)
        selectMenu(ivHome)
        setCategoryOn(selectedCategory)
        /*  ************************ END ADD VIEWS ************************* */


        /* ************************ CLICK LISTENERS ************************ */
        // PULL TO REFRESH
        refreshLayout!!.setOnRefreshListener { MainService.loadProducts(this) }
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

        // HOME
        ivHome.setOnClickListener {
            selectMenu(ivHome)
            isFavouriteList = false
            loadProducts()
        }
        // FAVOURITE
        ivFavourite.setOnClickListener {
            selectMenu(ivFavourite)
            isFavouriteList = true
            loadProducts()
        }
        // NOTIFICATION
        ivNotification.setOnClickListener {
            Tools.Show.message(this,"Painel de notificações ainda não disponível")
        }
        // PROFILE
        ivProfile.setOnClickListener {
            Tools.Show.message(this,"Perfil de conta ainda não disponível")
        }
        /* ********************** END CLICK LISTENERS ********************** */
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun selectMenu(imageView: ImageView) {
        val ivHome: ImageView = this.findViewById(R.id.iv_home)
        val ivHeart: ImageView = this.findViewById(R.id.iv_heart)
        val ivNotification: ImageView = this.findViewById(R.id.iv_notification)
        val ivProfile: ImageView = this.findViewById(R.id.iv_profile)

        ivHome.setImageDrawable(this.getDrawable(R.drawable.ic_home))
        ivHeart.setImageDrawable(this.getDrawable(R.drawable.ic_heart))
        ivNotification.setImageDrawable(this.getDrawable(R.drawable.ic_notification))
        ivProfile.setImageDrawable(this.getDrawable(R.drawable.ic_profile))

        when (imageView.id) {
            ivProfile.id -> ivProfile.setImageDrawable(this.getDrawable(R.drawable.ic_profile_color))
            ivHeart.id -> ivHeart.setImageDrawable(this.getDrawable(R.drawable.ic_heart_color))
            ivNotification.id -> ivNotification.setImageDrawable(this.getDrawable(R.drawable.ic_notification_color))
            else -> ivHome.setImageDrawable(this.getDrawable(R.drawable.ic_home_color))
        }
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
        val oldSelectedButton = this.findViewById(AppConfig.Component.getButtonCategoryId(selectedCategory)) as Button
        oldSelectedButton.background = this.getDrawable(R.drawable.ic_box_button_off)

        // Set new category selected
        selectedCategory = category

        // Select Button
        val selectedButton: Button = this.findViewById(AppConfig.Component.getButtonCategoryId(category))
        selectedButton.background = this.getDrawable(R.drawable.ic_box_button_on)

        // Reload Products
        loadProducts()
    }

    fun openProductDetails(product: Product) {
        Tools.Show.message(this, "Openning Product with id ${product.id}")
        // TODO Implement click listener to open product details page
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setFavouriteProduct(product: Product) {
        val favouriteButton = this.findViewById(AppConfig.Component.getImageViewFavouriteId(product)) as ImageView
        if (MainService.getUser().favouriteProducts.any { it == product }) {
            MainService.getUser().favouriteProducts.remove(product)
            favouriteButton.setImageDrawable(this.getDrawable(R.drawable.ic_heart))
        } else {
            MainService.getUser().favouriteProducts.add(product)
            favouriteButton.setImageDrawable(this.getDrawable(R.drawable.ic_heart_color))
        }
        // TODO Implement to set product as favourite on server or on shared preferences on device
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
            if (!isFavouriteList) {
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
            } else {
                if (MainService.getUser().favouriteProducts.any { it == product}) {
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

    override fun onDataLoaded() {
        loadProducts()
        false.also { refreshLayout!!.isRefreshing = it }
    }

}