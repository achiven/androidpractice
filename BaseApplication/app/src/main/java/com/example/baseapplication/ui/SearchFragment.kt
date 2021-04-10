package com.example.baseapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseapplication.R
import com.example.baseapplication.data.api.GitApis.Companion.GIT_REPO_POSITION
import com.example.baseapplication.data.model.OrganizationModel
import com.example.baseapplication.data.pagination.PaginationScrollListener
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment(), SearchAdapter.OnItemClickListener, SearchView.OnQueryTextListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    private val adapter = SearchAdapter(this)
    lateinit var viewModel: ViewModel
    private lateinit var paginationScrollListener: PaginationScrollListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(OrganizationModel::class.java)
        val linearLayoutManager = LinearLayoutManager(context)
        search_result_recyclerview.layoutManager = linearLayoutManager
        search_result_recyclerview.adapter = adapter

        search_bar.setOnQueryTextListener(this)

        (viewModel as OrganizationModel).repos.observe(requireActivity(),
            Observer {
                adapter.setItems(it)
            })

        paginationScrollListener = ConcretePaginationScrollListener(linearLayoutManager, ::loadMore)
        search_result_recyclerview.addOnScrollListener(paginationScrollListener)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
        }
    }


    class ConcretePaginationScrollListener(linearLayoutManager: LinearLayoutManager, loadMore: () -> Unit)
        : PaginationScrollListener(linearLayoutManager, loadMore)

    private fun loadMore(){
        val viewModel = ViewModelProvider(requireActivity()).get(OrganizationModel::class.java)
        var query = ""

        viewModel.repos.value?.isNotEmpty().let {
            query = viewModel.repos.value!![0].owner.login
            if(query.isNotEmpty())
                viewModel.retrieveRepos(query,
                    paginationScrollListener.currentPageCount, ::showProgress, ::hideProgress, ::showToast)
        }

    }

    private fun showProgress() {
        paginationScrollListener.isLoading = true
        adapter.addProgressBar()
    }

    private fun hideProgress() {
        paginationScrollListener.isLoading = false
        adapter.removeProgressBar()
    }

    private fun showToast(message: Int){
        Toast.makeText(requireContext(), getString(message), Toast.LENGTH_SHORT).show()
    }

    override fun onClick(pos: Int) { activity?.run {
            val bundle = Bundle()
            bundle.putInt(GIT_REPO_POSITION, pos)

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RepoDetailFragment::class.java, bundle)
                .addToBackStack("RepoDetailFragment")
                .commit()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.apply {
            if(isNewQuery){
                paginationScrollListener.reset()
                val viewModel = ViewModelProvider(requireActivity()).get(OrganizationModel::class.java)
                viewModel.repos.value?.isNotEmpty().let {
                    viewModel.repos.value?.clear()
                }
            }
            (viewModel as OrganizationModel).retrieveRepos(query,
                paginationScrollListener.currentPageCount, ::showProgress, ::hideProgress, ::showToast)
        }
        return false
    }

    var isNewQuery = false
    override fun onQueryTextChange(newText: String?): Boolean {
        isNewQuery = true
        return false
    }
}