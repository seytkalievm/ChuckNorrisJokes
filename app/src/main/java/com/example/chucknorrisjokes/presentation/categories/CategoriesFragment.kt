package com.example.chucknorrisjokes.presentation.categories

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.chucknorrisjokes.databinding.CategoriesFragmentBinding

class CategoriesFragment : Fragment() {

    private var _binding: CategoriesFragmentBinding? = null

    private val binding get() = _binding!!
    lateinit var viewModel: CategoriesViewModel
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CategoriesFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        navController = this.findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTranslationZ(requireView(), 100f)
        viewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
        binding.categoriesViewModel = viewModel

        binding.categoriesRv.adapter = CategoriesAdapter(CategoriesAdapter.OnClickListener{
            viewModel.displayJoke(it)
        })

        viewModel.navigateToSelectedCategory.observe(viewLifecycleOwner){
            if("null"!=it){
                navController.navigate(
                    CategoriesFragmentDirections.actionMainFragmentToJokeFragment(it)
                )
                viewModel.displayJokeComplete()
            }
        }
    }
}