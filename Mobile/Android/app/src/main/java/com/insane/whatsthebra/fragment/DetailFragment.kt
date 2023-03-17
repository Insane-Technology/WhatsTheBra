package com.insane.whatsthebra.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.insane.whatsthebra.R
import com.insane.whatsthebra.activity.MainActivity
import com.insane.whatsthebra.adapter.DetailImageAdapter
import com.insane.whatsthebra.config.AppConfig
import com.insane.whatsthebra.config.GlideApp
import com.insane.whatsthebra.database.AppDataBase
import com.insane.whatsthebra.databinding.FragmentDetailBinding
import com.insane.whatsthebra.model.BraType
import com.insane.whatsthebra.model.Product
import com.insane.whatsthebra.service.UserService
import com.insane.whatsthebra.utils.Tools
import kotlinx.coroutines.launch
import java.text.DecimalFormat

private const val PRODUCT = "PRODUCT_ID"

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private var productId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId = it.getInt(PRODUCT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        setUpViews()
        setUpClickListeners()

        return binding.root
    }

    private fun getStringFromList(braTypeList: ArrayList<BraType>): String {
        var string = ""
        for (braType in braTypeList) {
            braType.name.let { string = "$string, $it" }
        }
        return string.drop(1).drop(1)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private fun setUpViews() {
        val db = this.context?.let { AppDataBase.getDataBase(it) }

        lifecycleScope.launch {
            val product = db?.productDao()?.getById(productId)?.toProduct(db)
            val favouriteProducts = db?.let { UserService(it).getFavouriteProducts() }
            val dec = DecimalFormat("#,###.00")
            val priceAfterDiscount = product?.price?.minus((product.price * product.discount!! / 100))

            binding.textViewShopName.text = product?.shop?.name
            binding.textViewShopDescription.text = product?.description
            binding.textViewBraMatch.text = getStringFromList(product!!.braTypes)
            binding.textViewPrice.text = "R$ ${dec.format(priceAfterDiscount)}"

            if (product.productType.id == 3) {
                binding.textViewTitleRecommendation.text = context?.getString(R.string.productRecommendation)
                binding.textViewIdealTitle.text = context?.getString(R.string.braTypeTitle)
            } else {
                binding.textViewTitleRecommendation.text = context?.getString(R.string.braRecommendation)
                binding.textViewIdealTitle.text = context?.getString(R.string.idealBraTitle)
            }

            displayViewPager(product)
            displayRecommendations(product)

            if (favouriteProducts!!.any { it == product}) {
                binding.imageViewHeart.setImageDrawable(requireActivity().getDrawable(R.drawable.ic_heart_color))
            }

            binding.imageViewHeart.setOnClickListener {
                setFavouriteProduct(product)
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setFavouriteProduct(product: Product) {
        val context = this.context
        val db = AppDataBase.getDataBase(context!!)
        val favouriteButton = binding.imageViewHeart
        if (UserService(db).setFavouriteProduct(product))
            favouriteButton.setImageDrawable(context.getDrawable(R.drawable.ic_heart_color))
        else
            favouriteButton.setImageDrawable(context.getDrawable(R.drawable.ic_heart))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun displayRecommendations(product: Product) {
        val context = this.context
        val db = AppDataBase.getDataBase(context!!)
        val recommendations = ArrayList<Product>()
        val productTypes = db.productTypeDao().getAll()

        for (productType in productTypes) {
            if (productType.id != product.productType.id) {
                for (prod in db.productDao().getAllByType(productType.id)) {
                    for (braType in product.braTypes) {
                        if (prod.toProduct(db).braTypes.any { it == braType }) {
                            val productRecommendation = prod.toProduct(db)
                            recommendations.add(productRecommendation)
                            binding.linearLayoutRecommendations.let {
                                val productImage = AppConfig.Image.getImageViewRecommendationTemplate(context)
                                GlideApp.with(context)
                                    .load(AppConfig.API.getImageUrl(productRecommendation.images[0].id))
                                    .error(context.resources.getDrawable(R.drawable.ic_broken_image, context.theme))
                                    .fallback(context.resources.getDrawable(R.drawable.ic_broken_image, context.theme))
                                    .placeholder(AppConfig.Image.getLoader(context))
                                    .centerCrop()
                                    .into(productImage)

                                    it.addView(productImage, 0)

                                    // CLICK LISTENER
                                    productImage.setOnClickListener {
                                        if (this.activity is MainActivity) {
                                            closeFragment().also {
                                                (this.activity as MainActivity).openProductDetails(productRecommendation)
                                            }
                                        }
                                    }

                            }
                        }
                    }
                }
            }
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private fun displayViewPager(product: Product) {
        val context = this.context
        val productImages = ViewPager(context!!)

        // CREATE DOTS LINEARLAYOUT \\
        val dotsLayout = LinearLayout(activity)
        val paramsLinearDot = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        paramsLinearDot.setMargins(0,Tools.Window.convertDpToPixel(0F),0,Tools.Window.convertDpToPixel(0F))
        dotsLayout.layoutParams = paramsLinearDot
        dotsLayout.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
        dotsLayout.orientation = LinearLayout.HORIZONTAL

        // CREATE DOTS LINEARLAYOUT \\
        val dotsContentLayout = RelativeLayout(activity)
        val paramsDotsContentLayout = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT)
        paramsDotsContentLayout.setMargins(0,Tools.Window.convertDpToPixel(0F),0,Tools.Window.convertDpToPixel(0F))
        dotsContentLayout.setPadding(0, 0, 0, Tools.Window.convertDpToPixel(35F))
        dotsContentLayout.layoutParams = paramsDotsContentLayout
        dotsContentLayout.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM

        productImages.layoutParams = LinearLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT)
        productImages.adapter = DetailImageAdapter(context, product)
        productImages.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int,positionOffset: Float,positionOffsetPixels: Int) { }
            override fun onPageSelected(position: Int) { fillLayoutDots(dotsLayout, product.images.size, position) }
            override fun onPageScrollStateChanged(state: Int) { }
        })

        binding.cardViewDetail.addView(productImages)
        binding.cardViewDetail.addView(dotsContentLayout)
        fillLayoutDots(dotsLayout, product.images.size, 0)
        dotsContentLayout.addView(dotsLayout, 0)
    }

    private fun fillLayoutDots(linearLayout: LinearLayout, amount: Int, position: Int) {
        linearLayout.removeAllViews()
        for (i in 0 until amount) {
            val dot = ImageView(activity)
            Tools.Window.convertDpToPixel(5F).let { dot.setPadding(it,it,it,it) }
            dot.scaleType = ImageView.ScaleType.CENTER_CROP
            if (position == i) dot.setImageResource(R.drawable.ic_dot_full) else dot.setImageResource(R.drawable.ic_dot_empty)
            linearLayout.addView(dot)
        }
    }

    private fun setUpClickListeners() {

        // FRAME LAYOUT DETAIL TO AVOID CLICK UNDERNEATH
        binding.frameLayoutBackground.setOnClickListener { /* Ignore event */ }

        // BUTTON CLOSE FRAGMENT
        binding.imageViewBack.setOnClickListener {
            closeFragment()
        }
    }

    private fun closeFragment() {
        val manager = requireActivity().supportFragmentManager
        if (this.activity is MainActivity) {
            (this.activity as MainActivity).loadProducts().also {
                manager.beginTransaction().remove(this).commit()
            }
        } else
            manager.beginTransaction().remove(this).commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(productId: Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(PRODUCT, productId)
                }
            }
    }

}