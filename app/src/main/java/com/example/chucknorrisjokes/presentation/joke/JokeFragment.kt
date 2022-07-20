package com.example.chucknorrisjokes.presentation.joke

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.chucknorrisjokes.common.Resource
import com.example.chucknorrisjokes.databinding.JokeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class JokeFragment : Fragment() {

    private lateinit var binding: JokeFragmentBinding

    private val viewModel: JokeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = JokeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var shown = true


        viewModel.saveStatus.observe(viewLifecycleOwner){
            if (!shown){
                shown = true
                Log.i("JokeFragment", "onViewCreated: saveStatus:${getString(it)}")
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.status.observe(viewLifecycleOwner){ status ->
            when (status){
                is Resource.Loading -> {
                    binding.apply {
                        isLoaded = false
                        isError = false
                        isLoading = true
                    }
                }
                is Resource.Success -> {
                    binding.apply {
                        isLoading = false
                        isError = false
                        isLoaded = true
                        joke = status.data
                    }
                }
                is Resource.Error -> {
                    binding.apply {
                        isLoading = false
                        isLoaded = false
                        isError = true
                        jokesFragmentErrorText.text = status.message
                    }
                }
            }
        }

        binding.jokeFragmentUpdateBtn.setOnClickListener {
            viewModel.updateJoke()
        }

        binding.jokesFragmentTryAgainBtn.setOnClickListener {
            viewModel.updateJoke()
        }

        binding.jokeFragmentAddToFavoritesBtn.setOnClickListener{
            shown = false
            viewModel.addJokeToFavorites()
        }

    }


}