package com.example.moviesdb.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdb.MainActivity
import com.example.moviesdb.databinding.FragmentSearchMovieBinding
import com.example.moviesdb.databinding.MovieSearchKeyItemBinding
import com.example.moviesdb.model.SearchKeyModel
import com.example.moviesdb.viewmodel.SearchMovieViewModel

class SearchMovieFragment : Fragment(), SearchView.OnQueryTextListener {
    lateinit var binding: FragmentSearchMovieBinding

    private val viewModel: SearchMovieViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProviders.of(this, SearchMovieViewModel.Factory(activity.application))
            .get(SearchMovieViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        (activity as MainActivity).setToolbarTitle("MoviesDb")
        (activity as MainActivity).setBackButtonEnabled(false)
        val adapter = SearchKeyRVAdapter(ItemClick {
            insertKeyInDbAndNavigateToMovieListFragment(it.key)
        })
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            recyclerView.adapter = adapter
        }
        binding.searchView.setOnQueryTextListener(this)

        viewModel.searchKeyList.observe(this, Observer {
            adapter.searchKeys = it
            binding.textView.visibility = View.GONE
        })
        return binding.root
    }

    private fun insertKeyInDbAndNavigateToMovieListFragment(key: String) {
        viewModel.insertKey(key)
        binding.root.findNavController()
            .navigate(
                SearchMovieFragmentDirections
                    .actionSearchMovieFragmentToMovieListFragment(key)
            )
    }

    override fun onQueryTextSubmit(key: String?): Boolean {
        key?.let {
            viewModel.insertKey(key)
            insertKeyInDbAndNavigateToMovieListFragment(key)
        }
        return false
    }

    override fun onQueryTextChange(key: String?): Boolean {
        return true
    }
}

class ItemClick(val block: (SearchKeyModel) -> Unit) {

    fun onClick(searchKeyModel: SearchKeyModel) = block(searchKeyModel)
}

class SearchKeyRVAdapter(val callback: ItemClick) : RecyclerView.Adapter<SearchKeyViewHolder>() {

    var searchKeys: List<SearchKeyModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchKeyViewHolder {
        val withDataBinding: MovieSearchKeyItemBinding = MovieSearchKeyItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchKeyViewHolder(withDataBinding)
    }

    override fun getItemCount() = searchKeys.size

    override fun onBindViewHolder(holder: SearchKeyViewHolder, position: Int) {
        holder.movieSearchKeyItemBinding.also {
            it.searchKeyModel = searchKeys[position]
            it.callback = callback
        }
    }

}

class SearchKeyViewHolder(var movieSearchKeyItemBinding: MovieSearchKeyItemBinding) :
    RecyclerView.ViewHolder(movieSearchKeyItemBinding.root) {
}