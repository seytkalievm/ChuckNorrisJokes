package com.example.chucknorrisjokes.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorrisjokes.databinding.SearchResultItemBinding
import com.example.chucknorrisjokes.data.local.Joke

class SearchResultsAdapter(private val onClicklistener: OnClickListener): ListAdapter<Joke, SearchResultsAdapter.SearchResultViewHolder>(
    DiffCallback
) {
    class SearchResultViewHolder(private var binding: SearchResultItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(joke: Joke) {
            binding.joke.text = joke.value
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Joke>() {
        override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.value == oldItem.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            SearchResultViewHolder {
        return SearchResultViewHolder(SearchResultItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val joke = getItem(position)
        holder.itemView.setOnClickListener{
            onClicklistener.onClick(joke)
        }
        holder.bind(joke)

    }

    class OnClickListener(val clickListener: (joke: Joke) -> Unit){
        fun onClick(joke: Joke) = clickListener(joke)
    }
}