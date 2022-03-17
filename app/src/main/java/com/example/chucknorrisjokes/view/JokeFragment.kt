package com.example.chucknorrisjokes.view

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.chucknorrisjokes.databinding.JokeFragmentBinding
import com.example.chucknorrisjokes.model.database.Joke
import com.example.chucknorrisjokes.viewmodel.JokeViewModel
import com.example.chucknorrisjokes.viewmodel.viewModelFactories.JokeViewModelFactory

class JokeFragment : Fragment() {

    private var _binding: JokeFragmentBinding? = null
    private val binding: JokeFragmentBinding get() = _binding!!

    private lateinit var viewModel: JokeViewModel
    private lateinit var viewModelFactory: JokeViewModelFactory




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = JokeFragmentBinding.inflate(inflater, container, false)

        val application = requireNotNull(activity).application
        val category = JokeFragmentArgs.fromBundle(
            requireArguments()
        ).selectedCategory

        binding.lifecycleOwner = this
        viewModelFactory = JokeViewModelFactory(category, application)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[JokeViewModel::class.java]
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