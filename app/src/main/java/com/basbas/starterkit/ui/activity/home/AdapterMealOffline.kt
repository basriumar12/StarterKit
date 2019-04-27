package com.basbas.starterkit.ui.activity.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basbas.starterkit.R
import com.basbas.starterkit.entity.MealsItem
import com.basbas.starterkit.entity.MealsItemOffline
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.meal_item.view.*

class AdapterMealOffline (val context: Context, val meals: List<MealsItemOffline?>) : RecyclerView.Adapter<AdapterMealOffline.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.meal_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = meals[position]

        holder.itemView.tv_item_name.text = meal?.title
        Glide.with(context)
            .load(meal?.photo)
            .into(holder.itemView.iv_item_image)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}