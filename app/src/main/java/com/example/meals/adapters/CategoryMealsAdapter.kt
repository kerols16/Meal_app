package com.example.meals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meals.activities.CategoryMealsActivity
import com.example.meals.databinding.MealCardBinding
import com.example.meals.pojo.MealsByCategory

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {
    private var mealsList = ArrayList<MealsByCategory>()
    private lateinit var controller : CategoryMealsActivity

    // Update function to set meals list
    fun setMealsList(mealList: List<MealsByCategory> , controller : CategoryMealsActivity) {
        this.mealsList = ArrayList(mealList)  // Ensure correct type
        this.controller = controller
        notifyDataSetChanged()
    }

    // ViewHolder class to hold binding
    inner class CategoryMealsViewHolder(val binding: MealCardBinding) : RecyclerView.ViewHolder(binding.root)

    // Inflate the layout and create ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        val binding = MealCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryMealsViewHolder(binding)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        val meal = mealsList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = meal.strMeal

        holder.itemView.setOnClickListener{
            controller.onMealClick(mealsList[position])
        }
    }

    // Return the size of the dataset
    override fun getItemCount(): Int {
        return mealsList.size
    }





}
