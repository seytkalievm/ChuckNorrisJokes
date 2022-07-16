package com.example.chucknorrisjokes.presentation.joke

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chucknorrisjokes.databinding.JokeFragmentBinding
import com.example.chucknorrisjokes.data.local.Joke
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class JokeFragment : Fragment() {

    private var _binding: JokeFragmentBinding? = null
    private val binding: JokeFragmentBinding get() = _binding!!

    private val viewModel: JokeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = JokeFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.jokeViewModel = viewModel

        binding.updateButton.setOnClickListener {
            viewModel.updateJoke()
        }

        binding.backButton.setOnClickListener {
            this.findNavController().popBackStack()
        }

        binding.addToFavorites.setOnClickListener{
            addJoke(viewModel.joke.value!!)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun addJoke(joke: Joke){
        viewModel.addJokeToFavorites(joke)
    }


}