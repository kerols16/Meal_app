package com.example.meals.viewModle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meals.pojo.MealsByCategory
import com.example.meals.pojo.MealsByCategoryList
import com.example.meals.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealViewModel : ViewModel() {
    // Change this to List<MealsByCategory>
    val mealsliLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String) {
        RetrofitInstance.api.getmealsByCategory(categoryName).enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let { mealsList ->

                    // Set the value to the LiveData
                    mealsliLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("CategoryMeals", t.message.toString())
            }

        })
    }
    fun observeMealsLiveData ():LiveData<List<MealsByCategory>>{
        return mealsliLiveData
    }
}
