package com.example.meals.viewModle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meals.db.MealDatabase
import com.example.meals.pojo.Meal
import com.example.meals.pojo.MealList
import com.example.meals.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    val mealDatabase: MealDatabase
): ViewModel() {
    private var mealDetailLiveData = MutableLiveData<Meal>()
    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetail(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body()!=null) {
                    mealDetailLiveData.value = response.body()!!.meals[0]
                }else return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("maelData", "onFailure:${t.message.toString()}")
            }


        })
    }
    fun observerMealDetailLiveData():LiveData<Meal>{
        return mealDetailLiveData
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
}