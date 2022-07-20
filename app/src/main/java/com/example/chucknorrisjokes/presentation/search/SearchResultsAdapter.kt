package com.example.chucknorrisjokes.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorrisjokes.databinding.SearchResultItemBinding
import com.example.chucknorrisjokes.domain.model.joke.Joke

class SearchResultsAdapter(private val onClickListener: OnClickListener): ListAdapter<Joke, SearchResultsAdapter.SearchResultViewHolder>(
    DiffCallback
) {
    class SearchResultViewHolder(private var binding: SearchResultItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(joke: Joke) {
            binding.joke.text = joke.joke
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Joke>() {
        override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.joke == oldItem.joke
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            SearchResultViewHolder {
        return SearchResultViewHolder(SearchResultItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val joke = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(joke)
        }
        holder.bind(joke)

    }

    class OnClickListener(val clickListener: (joke: Joke) -> Unit){
        fun onClick(joke: Joke) = clickListener(joke)
    }
}