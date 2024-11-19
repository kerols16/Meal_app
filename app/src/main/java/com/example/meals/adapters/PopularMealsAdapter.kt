package com.example.meals.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meals.databinding.PopulerItemsBinding
import com.example.meals.pojo.MealsByCategory

class PopularMealsAdapter (): RecyclerView.Adapter<PopularMealsAdapter.PopularMealViewHolder>() {

    lateinit var  popName : TextView
    private var  mealsList = ArrayList<MealsByCategory>()
    lateinit var onItemClick : ((MealsByCategory)->Unit)
    @SuppressLint("NotifyDataSetChanged")
    fun setMeals(mealsList:  ArrayList<MealsByCategory> ){
       this.mealsList =mealsList
        notifyDataSetChanged()

    }
    class PopularMealViewHolder (val binding: PopulerItemsBinding ) : RecyclerView.ViewHolder(binding.root) {
        val popName: TextView = binding.txtPopularMeal
    }
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {

    return PopularMealViewHolder(PopulerItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
       holder.popName.text=mealsList[position].strMeal
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMeal)

        holder.itemView.setOnClickListener{
        onItemClick.invoke(mealsList[position])

        }
    }




}