package com.example.chucknorrisjokes.presentation.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.chucknorrisjokes.R
import com.example.chucknorrisjokes.databinding.FavoritesFragmentBinding
import com.example.chucknorrisjokes.domain.model.joke.display
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =  FavoriteJokesAdapter(
            FavoriteJokesAdapter.OnClickListener{ joke ->
                context?.let { context ->
                    joke.display(
                        context,
                        positiveButtonClickListener = {viewModel.deleteJoke(joke)},
                        positiveButtonTitle = R.string.delete_from_favourites,
                    )
                }
            }
        )

        binding.FavoritesRecyclerView.adapter = adapter

        viewModel.jokes.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

    }

}