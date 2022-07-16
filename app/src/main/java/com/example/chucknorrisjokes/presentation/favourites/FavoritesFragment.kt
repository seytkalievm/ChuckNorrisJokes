package com.example.chucknorrisjokes.presentation.favourites

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.chucknorrisjokes.databinding.FavoritesFragmentBinding
import com.example.chucknorrisjokes.data.local.Joke
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FavoritesFragmentBinding
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoritesFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.FavoritesRecyclerView.adapter = FavoriteJokesAdapter(FavoriteJokesAdapter.OnClickListener{
            displayJoke(it)
        })

    }


    private fun displayJoke(joke: Joke){
        val alertDialog = AlertDialog.Builder(activity)

        if (joke.categories.isEmpty()) alertDialog.setTitle("No category") else
            alertDialog.setTitle(joke.categories[0])

        alertDialog.setMessage(joke.value)
        alertDialog.setPositiveButton("Delete from favorites") { _,_->
            viewModel.deleteJoke(joke)
        }
        alertDialog.setNegativeButton("Back") { _, _ -> }
        alertDialog.show()
    }

}