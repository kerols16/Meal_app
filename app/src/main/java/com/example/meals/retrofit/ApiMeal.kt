package com.example.meals.retrofit

import com.example.meals.pojo.CategoryList
import com.example.meals.pojo.MealsByCategoryList
import com.example.meals.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiMeal {
    @GET("random.php")
    fun getRandMeal():Call<MealList>

    @GET ("lookup.php?")
    fun getMealDetail(@Query("i") id:String):Call<MealList>

    @GET("search.php?")
    fun getPopularItemS(@Query("s") categoryName:String):Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories (): Call<CategoryList>

    @GET("filter.php")
    fun getmealsByCategory(@Query("c")categoryName: String):Call<MealsByCategoryList>
}