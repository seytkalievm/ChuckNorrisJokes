package com.example.chucknorrisjokes.presentation.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.chucknorrisjokes.common.Resource
import com.example.chucknorrisjokes.databinding.CategoriesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private var _binding: CategoriesFragmentBinding? = null

    private val binding get() = _binding!!
    private val viewModel: CategoriesViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CategoriesFragmentBinding.inflate(inflater, container, false)
        navController = this.findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTranslationZ(requireView(), 100f)

        val adapter = CategoriesAdapter(CategoriesAdapter
            .OnClickListener{ displayJoke(it.category) })

        binding.categoriesRv.adapter = adapter

        viewModel.categories.observe(viewLifecycleOwner){ resource ->
            when (resource){
                is Resource.Success -> {
                    binding.apply {
                        isError = false
                        isLoading = false
                        isLoaded = true
                    }
                    adapter.submitList(resource.data)
                }

                is Resource.Loading -> {
                    binding.apply {
                        isError = false
                        isLoaded = false
                        isLoading = true
                    }
                }

                is Resource.Error -> {
                    binding.apply {
                        isError = true
                        isLoading = false
                        isLoaded = false
                        categoriesFragmentErrorTv.text = resource.message
                    }

                }
            }
        }

    }

    private fun displayJoke(category: String){
        navController.navigate(
            CategoriesFragmentDirections.actionMainFragmentToJokeFragment(category)
        )
    }
}