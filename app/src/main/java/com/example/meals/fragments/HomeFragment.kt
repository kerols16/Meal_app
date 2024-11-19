package com.example.meals.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easyfood.adapters.CategoriesRecyclerAdapter
import com.example.meals.R
import com.example.meals.activities.CategoryMealsActivity
import com.example.meals.activities.MainActivity
import com.example.meals.activities.Mealsveiwer
import com.example.meals.adapters.PopularMealsAdapter
import com.example.meals.databinding.FragmentHomeBinding
import com.example.meals.pojo.Meal
import com.example.meals.viewModle.HomeViewModel
import java.security.interfaces.ECKey

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popItemsAdapter: PopularMealsAdapter
    private lateinit var categoriesAdapter: CategoriesRecyclerAdapter

    companion object {
        const val MealId = "com.example.meals.fragments.idMeal"
        const val MealName = "com.example.meals.fragments.nameMeal"
        const val MealThumb = "com.example.meals.fragments.thumbMeal"
        const val MealVideo = "com.example.meals.fragments.videoMeal"
        const val CATEGORY_NAME = "com.example.meals.fragments.CategoryNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize ViewModel and Adapters
        viewModel = (activity as MainActivity).viewModel
        popItemsAdapter = PopularMealsAdapter()
        categoriesAdapter = CategoriesRecyclerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Views
        val imgtxt: TextView = view.findViewById(R.id.txtRandImg)

        // Set up RecyclerViews and click listeners
        prepareCategoryRecyclerView()
        preperPopItemSRecyclerView()
        onRandommealClick()
        onPopularItemClick()
        onCategoryClick()

        // Fetch and observe data
        viewModel.getRandomMeal()
        observeRandomMeal()
        viewModel.getPopularItems()
        observePopularItemLiveData()
        viewModel.getCategories()
        observeCategoriesLiveData()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoryRecyclerView() {
        binding.categoryRecyclerView.apply {
            adapter = categoriesAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun preperPopItemSRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popItemsAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categories?.let {
                categoriesAdapter.setCategoryList(it)
            }
        })
    }

    private fun observePopularItemLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner, Observer { mealList ->
            mealList?.let {
                popItemsAdapter.setMeals(ArrayList(it))
            }
        })
    }

    private fun observeRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner, Observer { meal ->
            meal?.let {
                val imgtxt: TextView = view?.findViewById(R.id.txtRandImg) ?: return@Observer
                imgtxt.text = it.strMeal
                Glide.with(this)
                    .load(it.strMealThumb)
                    .into(binding.randimg)

                this.randomMeal = it
            }
        })
    }

    private fun onPopularItemClick() {
        popItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity, Mealsveiwer::class.java)
            intent.putExtra(MealId, meal.idMeal)
            intent.putExtra(MealName, meal.strMeal)
            intent.putExtra(MealThumb, meal.strMealThumb)
            intent.putExtra(MealVideo, meal.strYoutube)
            startActivity(intent)
        }
    }

    private fun onRandommealClick() {
        binding.randmealView.setOnClickListener {
            val intent = Intent(activity, Mealsveiwer::class.java)
            intent.putExtra(MealId, randomMeal.idMeal)
            intent.putExtra(MealName, randomMeal.strMeal)
            intent.putExtra(MealThumb, randomMeal.strMealThumb)
            intent.putExtra(MealVideo, randomMeal.strYoutube)
            startActivity(intent)
        }
    }
}
