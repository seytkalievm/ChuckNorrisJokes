package com.example.chucknorrisjokes.presentation.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorrisjokes.databinding.SearchResultItemBinding
import com.example.chucknorrisjokes.domain.model.joke.Joke

class FavoriteJokesAdapter(private val onClickListener: OnClickListener)
    : ListAdapter<Joke, FavoriteJokesAdapter.JokeViewHolder>(DiffCallback){

    class JokeViewHolder(private var binding: SearchResultItemBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(joke: Joke){
                binding.joke.text = joke.joke
            }

    }

    companion object DiffCallback: DiffUtil.ItemCallback<Joke>(){
        override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.joke == newItem.joke
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