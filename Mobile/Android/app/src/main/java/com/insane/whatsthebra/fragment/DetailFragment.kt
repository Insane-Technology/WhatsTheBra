package com.insane.whatsthebra.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.insane.whatsthebra.R
import com.insane.whatsthebra.database.AppDataBase
import com.insane.whatsthebra.databinding.FragmentDetailBinding
import com.insane.whatsthebra.service.UserService
import kotlinx.coroutines.async
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

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private fun setUpViews() {
        val db = this.context?.let { AppDataBase.getDataBase(it) }
        lifecycleScope.launch {
            val product = db?.productDao()?.getById(productId)?.toProduct(db)
            val favouriteProducts = db?.let { UserService(it).getFavouriteProducts() }
            val dec = DecimalFormat("#,###.00")

            binding.textViewShopName.text = product?.shop?.name
            binding.textViewShopDescription.text = product?.description
            binding.textViewBraMatch.text = product!!.braTypes[0].name
            binding.textViewPrice.text = "R$ ${dec.format(product.price)}"

            if (favouriteProducts!!.any { it == product}) {
                binding.imageViewHeart.setImageDrawable(requireActivity().getDrawable(R.drawable.ic_heart_color))
            }
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