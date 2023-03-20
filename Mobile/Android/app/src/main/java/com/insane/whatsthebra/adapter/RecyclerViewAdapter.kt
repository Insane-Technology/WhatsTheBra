package com.insane.whatsthebra.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.insane.whatsthebra.R
import com.insane.whatsthebra.activity.MainActivity
import com.insane.whatsthebra.config.AppConfig
import com.insane.whatsthebra.config.GlideApp
import com.insane.whatsthebra.database.AppDataBase
import com.insane.whatsthebra.model.Product
import com.insane.whatsthebra.service.UserService
import com.insane.whatsthebra.utils.Tools

@SuppressLint("SetTextI18n, UseCompatLoadingForDrawables")
class RecyclerViewAdapter(private val mList: List<Product>, private val context: MainActivity) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = mList[position]

        holder.tvDescription.text = product.description
        holder.tvPrice.text = Tools.Currency.toReal(product.price.minus((product.price * product.discount / 100)))

        if (product.discount > 0) {
            holder.tvDiscountPrice.text = Tools.Currency.toReal(product.price)
            holder.tvDiscountPrice.paintFlags = holder.tvDiscountPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.tvDiscountPercentage.text = "${product.discount}% OFF"
        }

        loadImage(product, holder.ivImage)
        setFavouriteImage(product, holder.ivFavourite)

        // CLICK LISTENERS
        holder.ivImage.setOnClickListener { context.openProductDetails(product) }
        holder.ivFavourite.setOnClickListener { setFavouriteProduct(product, holder.ivFavourite) }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val ivImage: ImageView = itemView.findViewById(R.id.imageViewProductImage)
        val ivFavourite: ImageView = itemView.findViewById(R.id.imageViewFavouriteImage)
        val tvDescription: TextView = itemView.findViewById(R.id.textViewProductDescription)
        val tvPrice: TextView = itemView.findViewById(R.id.textViewProductPrice)
        val tvDiscountPrice: TextView = itemView.findViewById(R.id.textViewProductDiscountPrice)
        val tvDiscountPercentage: TextView = itemView.findViewById(R.id.textViewProductDiscountPercentage)
    }

    private fun loadImage(product: Product, imageView: ImageView) {
        if (product.images.size > 0) {
            GlideApp.with(context)
                .load(AppConfig.API.getImageUrl(product.images[0].name))
                .error(context.resources.getDrawable(R.drawable.ic_broken_image, context.theme))
                .fallback(context.resources.getDrawable(R.drawable.ic_broken_image, context.theme))
                .placeholder(AppConfig.Image.getLoader(context))
                .centerCrop()
                .into(imageView)
        } else {
            imageView.setImageDrawable(context.resources.getDrawable(R.drawable.ic_broken_image, context.theme))
        }
    }

    private fun setFavouriteImage(product: Product, imageView: ImageView) {
        if (UserService.user.favouriteProducts.any { it == product })
            imageView.setImageDrawable(context.resources.getDrawable(R.drawable.ic_heart_color, context.theme))
        else
            imageView.setImageDrawable(context.resources.getDrawable(R.drawable.ic_heart, context.theme))
    }

    private fun setFavouriteProduct(product: Product, favouriteImageView: ImageView) {
        if (UserService(AppDataBase.getDataBase(context)).setFavouriteProduct(product))
            favouriteImageView.setImageDrawable(context.getDrawable(R.drawable.ic_heart_color))
        else
            favouriteImageView.setImageDrawable(context.getDrawable(R.drawable.ic_heart))
    }

}