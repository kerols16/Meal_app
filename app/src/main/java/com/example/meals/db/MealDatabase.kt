package com.example.meals.db

import android.annotation.SuppressLint
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.meals.pojo.Meal




@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao() : MealDao
    companion object{
        @Volatile
        var INSTANCE : MealDatabase? = null

        fun getInstance (context : android.content.Context):MealDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal.db",
                ).fallbackToDestructiveMigration()
                    .build()


            }
            return INSTANCE as MealDatabase
        }

    }
}