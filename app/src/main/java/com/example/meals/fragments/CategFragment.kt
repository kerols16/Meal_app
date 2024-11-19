package com.example.meals.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meals.R
import com.example.meals.activities.MainActivity
import com.example.meals.activities.Mealsveiwer
import com.example.meals.fragments.HomeFragment.Companion.MealId
import com.example.meals.fragments.HomeFragment.Companion.MealName
import com.example.meals.fragments.HomeFragment.Companion.MealThumb
import com.example.meals.fragments.HomeFragment.Companion.MealVideo
import com.example.meals.pojo.Meal
import com.example.meals.pojo.MealsByCategory
import com.example.meals.viewModle.HomeViewModel
import com.example.slidingroot.adaptors.MealsAdaptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategFragment : Fragment() {
    private lateinit var searchView: SearchView
    private lateinit var rcResults: RecyclerView
    private lateinit var viewModel : ViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        rcResults = view.findViewById(R.id.rcResults)
        rcResults.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        searchView = view.findViewById(R.id.ed_search)

        (viewModel as HomeViewModel).observePopularItemsLiveData().observe(viewLifecycleOwner, Observer { mealList ->
            mealList?.let {
                rcResults.adapter = MealsAdaptor(mealList, this@CategFragment)
            }
        })


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            @SuppressLint("SetTextI18n")
            override fun onQueryTextChange(newText: String?): Boolean {

                (viewModel as HomeViewModel).getPopularItems(newText.toString())

                return true
            }
        })
    }

    fun onMealClick(meal : MealsByCategory){
        val intent = Intent(activity, Mealsveiwer::class.java)
        intent.putExtra(MealId, meal.idMeal)
        intent.putExtra(MealName, meal.strMeal)
        intent.putExtra(MealThumb, meal.strMealThumb)
        intent.putExtra(MealVideo, meal.strYoutube)
        startActivity(intent)
    }



}
