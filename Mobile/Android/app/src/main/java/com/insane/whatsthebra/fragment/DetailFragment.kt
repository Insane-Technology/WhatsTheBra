package com.insane.whatsthebra.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.insane.whatsthebra.database.AppDataBase
import com.insane.whatsthebra.databinding.FragmentDetailBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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

    private fun setUpViews() {
        val db = this.context?.let { AppDataBase.getDataBase(it) }
        lifecycleScope.launch {
            val product = db?.productDao()?.getById(productId)?.toProduct(db)
            binding.textViewFragment.text = product?.name
        }
    }

    private fun setUpClickListeners() {

        // FRAME LAYOUT DETAIL TO AVOID CLICK UNDERNEATH
        binding.frameLayoutBackground.setOnClickListener { /* Ignore event */ }

        // BUTTON CLOSE FRAGMENT
        binding.buttonDetailClose.setOnClickListener {
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