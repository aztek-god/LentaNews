package dv.serg.lentanews.util

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class PageLoadManager(private val initCurrentPage: Int = 1, private val maxPage: Int = DEFAULT_INIT_MAX_PAGE, private val recyclerView: RecyclerView,
                      private val layoutManager: LinearLayoutManager, val loadObservable: LoadStateObservable, var loadAction: (Int) -> Unit) {

    constructor(layoutManager: LinearLayoutManager, loadObservable: LoadStateObservable, recyclerView: RecyclerView, loadAction: (Int) -> Unit) : this(INIT_START_PAGE_DEFAULT_INDEX, DEFAULT_INIT_MAX_PAGE,
            recyclerView, layoutManager, loadObservable, loadAction)

    private val paginationScrollListener: PaginationScrollListener = object : PaginationScrollListener(layoutManager) {
        override fun loadMoreItems() {
            loadAction.invoke(currentPage++)
        }

        override fun getTotalPageCount(): Int = maxPage

        override fun isLastPage(): Boolean = currentPage == maxPage

        override fun isLoading(): Boolean = loadObservable.isLoading
    }

    init {
        recyclerView.addOnScrollListener(paginationScrollListener)
    }


    fun load() {
        loadAction.invoke(currentPage++)
    }

    companion object {
        const val INIT_START_PAGE_DEFAULT_INDEX = 1
        const val DEFAULT_INIT_MAX_PAGE = 10
    }

    interface LoadStateObservable {
        val isLoading: Boolean
    }

    var currentPage: Int = initCurrentPage
}