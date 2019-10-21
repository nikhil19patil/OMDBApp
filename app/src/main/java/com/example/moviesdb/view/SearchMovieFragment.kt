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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdb.MainActivity
import com.example.moviesdb.databinding.FragmentSearchMovieBinding
import com.example.moviesdb.databinding.MovieSearchKeyItemBinding
import com.example.moviesdb.model.SearchKeyModel
import com.example.moviesdb.viewmodel.SearchMovieViewModel

class SearchMovieFragment : Fragment(), SearchView.OnQueryTextListener {
    lateinit var binding: FragmentSearchMovieBinding
    lateinit var adapter: SearchKeyRVAdapter

    private val viewModel: SearchMovieViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProviders.of(this, SearchMovieViewModel.Factory(activity.application))
            .get(SearchMovieViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.searchKeyList.observe(this, Observer {
            adapter.searchKeys = it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        (activity as MainActivity).setToolbarTitle("MoviesDb")
        (activity as MainActivity).setBackButtonEnabled(false)
        adapter = SearchKeyRVAdapter(ItemClick {
            insertKeyInDbAndNavigateToMovieListFragment(it.key)
        })
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recyclerView.adapter = adapter
        binding.searchView.setOnQueryTextListener(this)

        return binding.root
    }

    private fun insertKeyInDbAndNavigateToMovieListFragment(key: String) {
        if ((activity as MainActivity).connectedToNetwork) {
            viewModel.insertKey(key)
            binding.root.findNavController()
                .navigate(
                    SearchMovieFragmentDirections
                        .actionSearchMovieFragmentToMovieListFragment(key)
                )
        } else {
            (activity as MainActivity).displayOfflineSnack()
        }
    }

    override fun onQueryTextSubmit(key: String?): Boolean {
        key?.let {
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
            val diffCallback = MovieModelDiffCallBack(searchKeys, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
//            notifyDataSetChanged()
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
    RecyclerView.ViewHolder(movieSearchKeyItemBinding.root)

class MovieModelDiffCallBack(private val old: List<SearchKeyModel>, private val new: List<SearchKeyModel>): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].key == new[newItemPosition].key
    }

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].id === new[newItemPosition].id
    }
}