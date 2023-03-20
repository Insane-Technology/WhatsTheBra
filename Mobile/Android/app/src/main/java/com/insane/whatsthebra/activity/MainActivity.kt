package com.insane.whatsthebra.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.activity.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.internal.ViewUtils
import com.insane.whatsthebra.R
import com.insane.whatsthebra.adapter.RecyclerViewAdapter
import com.insane.whatsthebra.component.MainComponent
import com.insane.whatsthebra.database.AppDataBase
import com.insane.whatsthebra.database.dto.CategoryDTO
import com.insane.whatsthebra.databinding.ActivityMainBinding
import com.insane.whatsthebra.fragment.DetailFragment
import com.insane.whatsthebra.interfaces.DataCallBack
import com.insane.whatsthebra.model.BraType
import com.insane.whatsthebra.model.Category
import com.insane.whatsthebra.model.Product
import com.insane.whatsthebra.service.MainService
import com.insane.whatsthebra.service.UserService
import com.insane.whatsthebra.utils.Tools

@SuppressLint("UseCompatLoadingForDrawables")
class MainActivity : AppCompatActivity(), DataCallBack {

    private lateinit var binding: ActivityMainBinding
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewState: Parcelable
    private val db = AppDataBase.getDataBase(this)
    private val mainActivityComponent = MainComponent(this)
    private var selectedCategory = db.categoryDao().getAll()[0].toCategory()
    private var selectedFilters = ArrayList<BraType>()
    private var favouriteList = false


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        closeFragmentIfVisible()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        refreshLayout = binding.swipeRefreshLayout
        recyclerView = binding.recyclerView
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerViewState = layoutManager.onSaveInstanceState()!!
        // SET UP VIEWS
        setUpViews()
        // SET UP CLICK LISTENERS
        setUpClickListeners()
    }

    private fun setUpViews() {
        loadCategoryButtons(binding.linearLayoutCategoryButtons, db.categoryDao().getAll())
        loadFilters(binding.linearLayoutCheckboxContainerLeft,binding.linearLayoutCheckboxContainerRight)
        selectMenu(binding.imageViewHome)
        setCategoryOn(selectedCategory)
    }

    @SuppressLint("RestrictedApi")
    private fun setUpClickListeners() {

        // PULL TO REFRESH
        refreshLayout.setOnRefreshListener {
            // Save state on recycler view
            recyclerViewState = recyclerView.layoutManager!!.onSaveInstanceState()!!
            // Load products
            MainService.loadProducts(db, this)
        }

        // BUTTON FILTER
        binding.imageButtonFilter.setOnClickListener { openModalFilter(binding.constraintLayoutFilterModal) }

        // DARK BACKGROUND MODAL
        binding.frameLayoutFilterBackground.setOnClickListener { closeModalFilter(binding.constraintLayoutFilterModal) }

        // BUTTON CANCEL FILTER MODAL
        binding.buttonFilterCancel.setOnClickListener { closeModalFilter(binding.constraintLayoutFilterModal) }

        // BUTTON APPLY FILTER MODAL
        binding.buttonFilterApply.setOnClickListener {
            closeModalFilter(binding.constraintLayoutFilterModal)
            loadProducts()
        }

        // MODAL CONTAINER TO AVOID CLOSE MODAL ON MODAL's CLICK
        binding.linearLayoutFilterContainer.setOnClickListener { /* Ignore event */ }

        // BUTTON HOME
        binding.imageViewHome.setOnClickListener {
            selectMenu(binding.imageViewHome)
            setFavouriteList(false).also { loadProducts() }
        }

        // BUTTON FAVOURITE
        binding.imageViewHeart.setOnClickListener {
            selectMenu(binding.imageViewHeart)
            setFavouriteList(true).also { loadProducts() }
        }

        // BUTTON NOTIFICATION
        binding.imageViewNotification.setOnClickListener {
            selectMenu(binding.imageViewNotification).also {
                loadNotifications()
            }
        }

        // BUTTON PROFILE
        binding.imageViewProfile.setOnClickListener {
            selectMenu(binding.imageViewProfile).also {
                loadProfile()
            }
        }

        // SEARCH IF CLICKED ON BUTTON SEARCH
        binding.editTextSearch.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            val input = binding.editTextSearch
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                ViewUtils.hideKeyboard(currentFocus ?: View(this))
                input.clearFocus()
                if (input.text.replace(" ".toRegex(), "") == "") {
                    loadProducts()
                } else {
                    loadProducts(input.text.toString())
                }
                input.text.clear()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun selectMenu(imageView: ImageView) {

        binding.imageViewHome.setImageDrawable(this.getDrawable(R.drawable.ic_home))
        binding.imageViewHeart.setImageDrawable(this.getDrawable(R.drawable.ic_heart))
        binding.imageViewNotification.setImageDrawable(this.getDrawable(R.drawable.ic_notification))
        binding.imageViewProfile.setImageDrawable(this.getDrawable(R.drawable.ic_profile))

        when (imageView.id) {
            binding.imageViewProfile.id -> binding.imageViewProfile.setImageDrawable(this.getDrawable(R.drawable.ic_profile_color))
            binding.imageViewHeart.id -> binding.imageViewHeart.setImageDrawable(this.getDrawable(R.drawable.ic_heart_color))
            binding.imageViewNotification.id -> binding.imageViewNotification.setImageDrawable(this.getDrawable(R.drawable.ic_notification_color))
            else -> binding.imageViewHome.setImageDrawable(this.getDrawable(R.drawable.ic_home_color))
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
        binding.imageButtonFilter.background = this.getDrawable(R.drawable.ic_box_button_filter_on)
    }

    fun removeFilter(braType: BraType) {
        selectedFilters.remove(braType)
        if (selectedFilters.size <= 0)
            binding.imageButtonFilter.background = this.getDrawable(R.drawable.ic_box_button_filter)
    }

    fun setCategoryOn(category: Category) {
        // Unselect selected button
        val oldSelectedButton = this.findViewById(Tools.Component.getButtonCategoryId(selectedCategory)) as Button
        oldSelectedButton.background = this.getDrawable(R.drawable.ic_box_button_off)

        // Set new category selected
        selectedCategory = category

        // Select Button
        val selectedButton: Button = this.findViewById(Tools.Component.getButtonCategoryId(category))
        selectedButton.background = this.getDrawable(R.drawable.ic_box_button_on)

        // Reload Products
        loadProducts()
    }

    fun openProductDetails(product: Product) {
        // Save state
        recyclerViewState = recyclerView.layoutManager!!.onSaveInstanceState()!!
        // Load Fragment
        val detailFragment = DetailFragment.newInstance(product.id);
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frameLayoutDetail, detailFragment, "FRAGMENT_DETAIL")
            .commit();

    }

    private fun closeFragmentIfVisible() {
        val detailFragment: DetailFragment? = supportFragmentManager.findFragmentByTag("FRAGMENT_DETAIL") as DetailFragment?
        if (detailFragment != null && detailFragment.isVisible) {
            val manager = this.supportFragmentManager
            manager.beginTransaction().remove(detailFragment).commit()
            loadProducts()
        }
    }

    private fun checkCategoryProduct(product: Product): Boolean {
        if (selectedCategory.id <= 0) return true
        if (product.categories.any { it == selectedCategory})
            return true
        return false
    }

    private fun checkQueryProduct(product: Product, query: String): Boolean {
        if (query != "") {
            return product.description.contains(query, ignoreCase = true)
        }
        return true
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

    private fun loadNotifications() {
        cleanViews().also {
            binding.linearLayoutCategories.visibility = View.INVISIBLE
            binding.linearLayoutSearchBar.visibility = View.INVISIBLE
            binding.recyclerView.visibility = View.INVISIBLE
            binding.linearLayoutMessageContainer.addView(mainActivityComponent.createTextView(this.getString(R.string.notificationMessage), Gravity.CENTER))
        }
    }

    private fun loadProfile() {
        cleanViews().also {
            binding.linearLayoutCategories.visibility = View.INVISIBLE
            binding.linearLayoutSearchBar.visibility = View.INVISIBLE
            binding.recyclerView.visibility = View.INVISIBLE
            binding.linearLayoutMessageContainer.addView(mainActivityComponent.createTextView(this.getString(R.string.profileMessage), Gravity.CENTER))
        }
    }

    private fun cleanViews() {
        binding.linearLayoutCategories.visibility = View.VISIBLE
        binding.linearLayoutSearchBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.VISIBLE
        binding.linearLayoutMessageContainer.removeAllViews()
    }


    fun loadProducts(searchQuery: String = "") {
        var productMatches = 0
        val favouriteProducts = UserService(db).getFavouriteProducts()
        val productList = ArrayList<Product>()

        // CLEAR VIEWS
        cleanViews()

        fun filter(product: Product) {
            if (checkCategoryProduct(product) && checkQueryProduct(product, searchQuery)) {
                if (checkFilterProduct(product, selectedFilters)) {
                    productMatches++
                    productList.add(product)
                }
            }
        }

        fun getProducts() {
            for (product in db.productDao().getAll()) {
                if (!favouriteList) {
                    filter(product.toProduct(db))
                } else {
                    if (favouriteProducts.any { it == product.toProduct(db)}) {
                        filter(product.toProduct(db))
                    }
                }
            }
        }

        getProducts().also {
            recyclerView.adapter = RecyclerViewAdapter(productList, this)
            // Restore state
            recyclerView.layoutManager!!.onRestoreInstanceState(recyclerViewState);
            if (searchQuery != "")
                binding.linearLayoutMessageContainer.addView(mainActivityComponent.createTextView("${this.getString(R.string.searchingFor)} $searchQuery", Gravity.START))
            else
                binding.linearLayoutMessageContainer.addView(mainActivityComponent.createTextView("${this.getString(R.string.searchFound)} $productMatches", Gravity.START))
        }

    }

    private fun loadCategoryButtons(buttonsContainer: LinearLayout, categories: List<CategoryDTO>) {
        buttonsContainer.removeAllViews()
        for (category in categories) {
            mainActivityComponent.createButtonCategory(buttonsContainer, category.toCategory())
        }
    }

    private fun loadFilters(leftColumn: LinearLayout, rightColumn: LinearLayout) {
        cleanViews()
        for ((index, braType) in db.braTypeDao().getAll().withIndex()) {
            if (index % 2 == 0) {
                mainActivityComponent.createCheckboxFilter(leftColumn, braType.toBraType())
            } else {
                mainActivityComponent.createCheckboxFilter(rightColumn, braType.toBraType())
            }
        }
    }

    private fun setFavouriteList(status: Boolean) {
        favouriteList = status
    }

    override fun onDataLoaded() {
        selectMenu(binding.imageViewHome).also {
            setFavouriteList(false)
            loadProducts()
            refreshLayout.isRefreshing = false
        }
    }

}