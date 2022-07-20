package com.example.chucknorrisjokes.presentation.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorrisjokes.databinding.CategoryItemViewBinding
import com.example.chucknorrisjokes.domain.model.category.Category
import java.util.*


class CategoriesAdapter(private val onClickListener: OnClickListener): ListAdapter<Category, CategoriesAdapter.CategoryViewHolder>(
    DiffCallback
) {

    class CategoryViewHolder(private var binding: CategoryItemViewBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(category: Category){
                binding.categoryTitle.text = category.category.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewype: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(category)
        }
        holder.bind(category)
    }

    class OnClickListener(val clickListener: (category: Category) -> Unit){
        fun onClick(category: Category) = clickListener(category)
    }

}
