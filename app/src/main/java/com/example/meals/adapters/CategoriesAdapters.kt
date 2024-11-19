package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meals.databinding.CategoryItemBinding
import com.example.meals.pojo.Category

class CategoriesRecyclerAdapter : RecyclerView.Adapter<CategoriesRecyclerAdapter.CategoryViewHolder>() {

    private var categoryList: List<Category> = ArrayList()
    var onItemClick: ((Category) -> Unit)? = null
    private var onLongCategoryClick: OnLongCategoryClick? = null

    fun setCategoryList(categoryList: List<Category>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    fun setOnLongCategoryClick(onLongCategoryClick: OnLongCategoryClick) {
        this.onLongCategoryClick = onLongCategoryClick
    }

    class CategoryViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.binding.apply {
            tvCategoryName.text = category.strCategory

            Glide.with(holder.itemView)
                .load(category.strCategoryThumb)
                .into(imgCategory)
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(category)
        }

        holder.itemView.setOnLongClickListener {
            onLongCategoryClick?.onCategoryLongCLick(category)
            true
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    interface OnItemCategoryClicked {
        fun onClickListener(category: Category)
    }

    interface OnLongCategoryClick {
        fun onCategoryLongCLick(category: Category)
    }
}
