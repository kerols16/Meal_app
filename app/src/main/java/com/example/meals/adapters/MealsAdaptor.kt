package com.example.slidingroot.adaptors

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meals.R
import com.example.meals.fragments.CategFragment
import com.example.meals.pojo.Meal
import com.example.meals.pojo.MealsByCategory

class MealsAdaptor(private val data : List<MealsByCategory> , val controller : CategFragment) : RecyclerView.Adapter<MealsAdaptor.MealsView>() {

    inner class MealsView(val view : View) : RecyclerView.ViewHolder(view){
        val mealImage = view.findViewById<ImageView>(R.id.img_meal)!!
        val mealName = view.findViewById<TextView>(R.id.tv_meal_name)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsView {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.meal_card,parent,false)
        return MealsView(inflate)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MealsView, position: Int) {
        Glide.with(holder.view.context).load(data[position].strMealThumb).into(holder.mealImage)
        holder.mealName.text = data[position].strMeal.toString()
        holder.itemView.setOnClickListener{
            controller.onMealClick(data[position])
        }
    }
}