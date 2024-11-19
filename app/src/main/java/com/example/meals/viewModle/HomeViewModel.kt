package com.example.meals.viewModle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meals.db.MealDatabase
import com.example.meals.pojo.Category
import com.example.meals.pojo.CategoryList
import com.example.meals.pojo.MealsByCategoryList
import com.example.meals.pojo.MealsByCategory
import com.example.meals.pojo.Meal
import com.example.meals.pojo.MealList
import com.example.meals.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (
    private val mealDatabase: MealDatabase
) : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var populerItemsLiveDate = MutableLiveData<List<MealsByCategory>>()
    private var categoryLiveData = MutableLiveData<List<Category>>()
    private var favMealsLiveData =mealDatabase.mealDao().getAllMeals()
    fun getRandomMeal() {
        RetrofitInstance.api.getRandMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }
    fun insertMeal (meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }

    }
    fun   deleteMeal (meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }

    }
    fun getPopularItems(str: String ="ar") {
        RetrofitInstance.api.getPopularItemS(str).enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if (response.body() != null) {
                    populerItemsLiveDate.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null) {
                    val categoryList = response.body()!!
                    categoryLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return populerItemsLiveDate
    }
    fun observeCategoriesLiveData(): LiveData<List<Category>> {
        return categoryLiveData
    }
    fun observeFavMealsLiveData(): LiveData<List<Meal>> {
        return favMealsLiveData
    }
}
