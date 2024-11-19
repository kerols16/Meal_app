package com.example.meals.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.meals.R
import com.example.meals.db.MealDatabase
import com.example.meals.viewModle.HomeViewModel
import com.example.meals.viewModle.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
     val viewModel : HomeViewModel by lazy {
         val mealDatabase = MealDatabase.getInstance(this)
         val homeDefaultViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
         ViewModelProvider(this,homeDefaultViewModelProviderFactory)[HomeViewModel::class.java]
     }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btmNavs = findViewById<BottomNavigationView>(R.id.btmNv)
        val navController = Navigation.findNavController(this, R.id.mainfrag)
        NavigationUI.setupWithNavController(btmNavs,navController)
    }
}