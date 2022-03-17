package com.example.chucknorrisjokes.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorrisjokes.databinding.FavoritesFragmentBinding
import com.example.chucknorrisjokes.databinding.SearchResultItemBinding
import com.example.chucknorrisjokes.model.database.Joke

class FavoriteJokesAdapter(private val onClickListener: FavoriteJokesAdapter.OnClickListener)
    : ListAdapter<Joke, FavoriteJokesAdapter.JokeViewHolder>(DiffCallback){

    class JokeViewHolder(private var binding: SearchResultItemBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(joke: Joke){
                binding.joke.text = joke.value
            }

    }

    companion object DiffCallback: DiffUtil.ItemCallback<Joke>(){
        override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.url == oldItem.url
        }

        override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.value == newItem.value
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JokeViewHolder {
        return JokeViewHolder(SearchResultItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        val joke = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(joke)
        }
        return holder.bind(joke)
    }

    class OnClickListener (val clickListener: (joke: Joke) -> Unit){
        fun onClick(joke: Joke) = clickListener(joke)
    }
}