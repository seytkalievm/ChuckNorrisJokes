package com.example.chucknorrisjokes.presentation.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorrisjokes.databinding.CategoryItemViewBinding
import java.util.*


class CategoriesAdapter(private val onClickListener: OnClickListener): ListAdapter<String, CategoriesAdapter.CategoryViewHolder>(
    DiffCallback
) {

    class CategoryViewHolder(private var binding: CategoryItemViewBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(category: String){
                binding.categoryTitle.text = category.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
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

    class OnClickListener(val clickListener: (category: String) -> Unit){
        fun onClick(category: String) = clickListener(category)
    }

}
