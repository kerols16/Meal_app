package com.example.meals.activities

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.meals.R
import com.example.meals.adapters.CategoryMealsAdapter
import com.example.meals.databinding.ActivityCategoryMealsBinding
import com.example.meals.fragments.HomeFragment
import com.example.meals.fragments.HomeFragment.Companion.CATEGORY_NAME
import com.example.meals.fragments.HomeFragment.Companion.MealId
import com.example.meals.fragments.HomeFragment.Companion.MealName
import com.example.meals.fragments.HomeFragment.Companion.MealThumb
import com.example.meals.fragments.HomeFragment.Companion.MealVideo
import com.example.meals.pojo.MealsByCategory
import com.example.meals.viewModle.CategoryMealViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel: CategoryMealViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecyclerView()

        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealViewModel::class.java]

        val categoryName = intent.getStringExtra(HomeFragment.CATEGORY_NAME) ?: ""
        categoryMealsViewModel.getMealsByCategory(categoryName)

        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { meals ->
            meals?.let {
                binding.categoryCountText.text= " ${categoryName} can you make with ${meals.size.toString()} Recipe. "
                categoryMealsAdapter.setMealsList(it,this@CategoryMealsActivity)
            }
        })
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.mealRv.apply {
            layoutManager = GridLayoutManager(this@CategoryMealsActivity, 1, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }

    fun onMealClick(meal : MealsByCategory){
        val intent = Intent(this, Mealsveiwer::class.java)
        intent.putExtra(MealId, meal.idMeal)
        intent.putExtra(MealName, meal.strMeal)
        intent.putExtra(MealThumb, meal.strMealThumb)
        intent.putExtra(MealVideo, meal.strYoutube)
        startActivity(intent)
    }
}
