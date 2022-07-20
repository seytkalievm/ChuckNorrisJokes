package com.example.chucknorrisjokes.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.chucknorrisjokes.R
import com.example.chucknorrisjokes.common.Resource
import com.example.chucknorrisjokes.databinding.SearchFragmentBinding
import com.example.chucknorrisjokes.domain.model.joke.display
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: SearchFragmentBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SearchResultsAdapter(SearchResultsAdapter.OnClickListener{joke ->
            context?.let {
                joke.display(
                    it,
                    positiveButtonClickListener = {viewModel.addJoke(joke)},
                    positiveButtonTitle = R.string.add_to_favorites
                )
            }
        })

        binding.apply {
            resultsRecyclerView.adapter = adapter

            searchEditText.setOnClickListener{ hideKeyboard() }

            searchButton.setOnClickListener {
                search()
                hideKeyboard(it)
            }
        }

        viewModel.status.observe(viewLifecycleOwner){resource ->
            when (resource){
                is Resource.Loading -> {
                    binding.apply {
                        isError = false
                        isLoaded = false
                        isLoading = true
                        searchFragmentNoResultsTv.visibility = View.GONE
                    }
                }
                is Resource.Success -> {
                    binding.apply {
                        isError = false
                        isLoading = false
                        isLoaded = true
                        if (resource.data!!.isEmpty()) {
                            resultsRecyclerView.visibility = View.GONE
                            searchFragmentNoResultsTv.visibility = View.VISIBLE
                        } else{
                            searchFragmentNoResultsTv.visibility = View.GONE
                            resultsRecyclerView.visibility = View.VISIBLE
                            adapter.submitList(resource.data)
                        }
                    }
                }

                is Resource.Error -> {
                    binding.apply {
                        isLoaded = false
                        isLoading = false
                        isError = true
                    }
                }
            }

        }

    }

    private fun search(){
        val query = binding.searchEditText.text.toString().trim()
        if( query.length < 3)
            Toast.makeText(context, R.string.invalid_query, Toast.LENGTH_SHORT).show()
        else
            viewModel.search(query)
    }

    private fun hideKeyboard(view: View){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun hideKeyboard(){
        binding.searchEditText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                hideKeyboard(binding.root)
                search()
                return@OnKeyListener true
            }
            false
        })
    }

}