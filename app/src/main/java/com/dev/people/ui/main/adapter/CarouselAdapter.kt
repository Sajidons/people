package com.dev.people.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.people.R
import kotlinx.android.synthetic.main.item_layout.view.*

/**
 * Created on 11 Dec 2022 by Sajid
 * Adapter class for Carousel
 *
 */

class CarouselAdapter(private val carouselDataList: ArrayList<Int>) :
    RecyclerView.Adapter<CarouselAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    //data binding for the layout items
    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {

        Glide.with(holder.itemView.imageViewAvatar.context)
            .load(carouselDataList[position])
            .into(holder.itemView.imageViewAvatar)

    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

}