package com.example.meals.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.meals.pojo.Meal
import com.example.meals.pojo.MealList

@Dao
interface MealDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsert(meal: Meal)

  @Delete
  suspend fun delete(meal: Meal)

  @Query("SELECT * FROM mealInfo")
    fun getAllMeals():LiveData<List<Meal>>


}