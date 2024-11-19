package com.example.meals.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.meals.R
import com.example.meals.databinding.ActivityMealsveiwerBinding
import com.example.meals.db.MealDatabase
import com.example.meals.fragments.HomeFragment
import com.example.meals.pojo.Meal
import com.example.meals.viewModle.MealViewModel
import com.example.meals.viewModle.MealViewModelFactory

class Mealsveiwer : AppCompatActivity() {
    private lateinit var idMeal: String
    private lateinit var nameMeal: String
    private lateinit var thumbMeal: String
    private var vidMeal: String? = null
    private lateinit var mealMvvm: MealViewModel

    private lateinit var binding: ActivityMealsveiwerBinding

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealsveiwerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this, viewModelFactory).get(MealViewModel::class.java)

        getMealInfo()
        setInfoInView()
        loadingCase()

        mealMvvm.getMealDetail(idMeal)
        observeMealDetailLiveData()
        onFavClick()

        vidMeal?.let { videoUrl ->
            if (videoUrl.isNotEmpty()) {
                val webView: WebView = binding.webVideo
                val rowUrl = videoUrl.substringAfter("v=") // Extract the video ID correctly
                val embedUrl = "https://www.youtube.com/embed/$rowUrl"

                val video = """
                <iframe width="100%" height="500px" src="$embedUrl"
                title="YouTube video player" frameborder="0"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
            """.trimIndent()

                webView.settings.javaScriptEnabled = true
                webView.loadData(video, "text/html", "utf-8")
                webView.webChromeClient = WebChromeClient()
            }
        }
    }

    private fun onFavClick() {
        binding.addtofavbtn.setOnClickListener {
            saveMeal?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Recipe Saved", Toast.LENGTH_LONG).show()
            }
        }
    }

    private var saveMeal: Meal? = null

    @SuppressLint("SetTextI18n")
    private fun observeMealDetailLiveData() {
        mealMvvm.observerMealDetailLiveData().observe(this) { meal ->
            respondCase()
            saveMeal = meal
            binding.txtcat.text = "The Category ${meal.strCategory}"
            binding.txtnat.text = "The Nationality ${meal.strArea}"
            binding.theMainIns.text = meal.strInstructions
        }
    }

    private fun setInfoInView() {
        Glide.with(applicationContext)
            .load(thumbMeal)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = nameMeal
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInfo() {
        val intent = intent
        idMeal = intent.getStringExtra(HomeFragment.MealId).orEmpty()
        nameMeal = intent.getStringExtra(HomeFragment.MealName).orEmpty()
        thumbMeal = intent.getStringExtra(HomeFragment.MealThumb).orEmpty()
        vidMeal = intent.getStringExtra(HomeFragment.MealVideo)
    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.addtofavbtn.visibility = View.INVISIBLE
        binding.theMainIns.visibility = View.INVISIBLE
        binding.txtcat.visibility = View.INVISIBLE
        binding.txtnat.visibility = View.INVISIBLE
        binding.webVideo.visibility = View.INVISIBLE
    }

    private fun respondCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.addtofavbtn.visibility = View.VISIBLE
        binding.theMainIns.visibility = View.VISIBLE
        binding.txtcat.visibility = View.VISIBLE
        binding.txtnat.visibility = View.VISIBLE
        binding.webVideo.visibility = View.VISIBLE
    }
}
