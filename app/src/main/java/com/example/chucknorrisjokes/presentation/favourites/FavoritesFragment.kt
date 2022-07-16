package com.example.chucknorrisjokes.presentation.favourites

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chucknorrisjokes.databinding.FavoritesFragmentBinding
import com.example.chucknorrisjokes.data.local.Joke
import com.example.chucknorrisjokes.viewmodel.viewModelFactories.FavoritesViewModelFactory

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var binding: FavoritesFragmentBinding
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var viewModelFactory: FavoritesViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        binding = FavoritesFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        viewModelFactory = FavoritesViewModelFactory(application)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[FavoritesViewModel::class.java]
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