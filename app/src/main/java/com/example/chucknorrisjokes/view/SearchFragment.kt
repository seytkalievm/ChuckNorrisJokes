package com.example.chucknorrisjokes.view

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.chucknorrisjokes.R
import com.example.chucknorrisjokes.databinding.SearchFragmentBinding
import com.example.chucknorrisjokes.view.adapters.SearchResultsAdapter
import com.example.chucknorrisjokes.model.database.Joke
import com.example.chucknorrisjokes.viewmodel.SearchViewModel
import com.example.chucknorrisjokes.viewmodel.viewModelFactories.SearchViewModelFactory

class SearchFragment : Fragment() {

    private lateinit var binding: SearchFragmentBinding
    private lateinit var viewModelFactory: SearchViewModelFactory
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(activity).application
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        viewModelFactory = SearchViewModelFactory(application)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
        binding.viewModel = viewModel

        binding.resultsRecyclerView.adapter = SearchResultsAdapter(SearchResultsAdapter.OnClickListener{
            displayJoke(it)
        })

        binding.searchEditText.setOnClickListener{
            hideKeyboard()
        }

        binding.searchButton.setOnClickListener {
            search()
            hideKeyboard(it)
        }

        viewModel.total.observe(viewLifecycleOwner){
            if(it.equals(0)){
                binding.resultsRecyclerView.visibility = View.INVISIBLE
                binding.textView3.visibility = View.VISIBLE
            }else{
                binding.resultsRecyclerView.visibility = View.VISIBLE
                binding.textView3.visibility = View.GONE
            }
        }

    }

    private fun search(){
        val query = binding.searchEditText.text.toString()
        val toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
        if( query.length < 3){
            toast.setText(R.string.invalid_query)
            toast.show()
        } else {
            viewModel.search(query)
        }
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

    private fun displayJoke(joke: Joke){
        val alertDialog = AlertDialog.Builder(activity)

        if (joke.categories.isEmpty()) alertDialog.setTitle("No category") else
            alertDialog.setTitle(joke.categories[0])

        alertDialog.setMessage(joke.value)
        alertDialog.setPositiveButton("Add to favorites") { _,_->
            viewModel.addJoke(joke)
        }
        alertDialog.setNegativeButton("Back") { _, _ -> }
        alertDialog.show()
    }


}