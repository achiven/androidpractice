package com.example.baseapplication.data.pagination

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


// https://www.journaldev.com/24041/android-recyclerview-load-more-endless-scrolling
// https://www.oodlestechnologies.com/blogs/implementing-pagination-scroll-listener-in-android/

abstract class PaginationScrollListener(
    var layoutManager: LinearLayoutManager,
    val loadMore: () -> Unit
) : RecyclerView.OnScrollListener() {
    companion object {
        const val FIRST_PAGE = 1
        const val TIME_DELAY = 1000
    }

    var currentPageCount: Int = FIRST_PAGE
    var isLoading: Boolean = false
    var lastTimeStamp = 0L

    init {
        reset()
    }

    fun reset() {
        currentPageCount = FIRST_PAGE
        isLoading = false
        lastTimeStamp = System.currentTimeMillis()
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount

        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
                && isDelayed()
            ) {
                isLoading = true
                currentPageCount++
                loadMore()
            }
        }
    }

    private fun isDelayed(): Boolean {
        if(lastTimeStamp + TIME_DELAY < System.currentTimeMillis()){
            lastTimeStamp = System.currentTimeMillis()
            return true
        }

        return false
    }
}