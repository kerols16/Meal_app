package com.example.meals.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.meals.R
import com.example.meals.activities.MainActivity
import com.example.meals.adapters.FavMealAdabter

import com.example.meals.databinding.FragmentFavBinding
import com.example.meals.viewModle.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavFragment : Fragment() {
    private lateinit var binding: FragmentFavBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favMealAdapter : FavMealAdabter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFav()
        prepareRecyclerView()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val meal = favMealAdapter.differ.currentList[position]
                viewModel.deleteMeal(meal)
                Snackbar.make(requireView(), "Recipe Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Think Again") {
                        viewModel.insertMeal(meal)
                    }.show()
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFav)
    }

    private fun prepareRecyclerView() {
        favMealAdapter = FavMealAdabter()
        binding.rvFav.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = favMealAdapter
        }
    }

    private fun observeFav() {
        viewModel.observeFavMealsLiveData().observe(viewLifecycleOwner, Observer { meals ->
            favMealAdapter.differ.submitList(meals)
        })
    }
}
