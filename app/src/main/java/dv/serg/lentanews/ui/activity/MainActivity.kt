package dv.serg.lentanews.ui.activity

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.Toast
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import dv.serg.lentanews.R
import dv.serg.lentanews.abstr.AbstractSwipeActivity
import dv.serg.lentanews.adapter.ListItemAdapter
import dv.serg.lentanews.contract.MainContract
import dv.serg.lentanews.pojo.Article
import dv.serg.lentanews.ui.presenter.MainPresenter
import dv.serg.lentanews.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_activity_list.*
import javax.inject.Inject


class MainActivity : AbstractSwipeActivity(), PageLoadManager.LoadStateObservable, MainContract.MainView<List<Article>>, SwipeRefreshLayout.OnRefreshListener,
        NavigationView.OnNavigationItemSelectedListener, ListItemAdapter.MaintainBookmarkState {


    companion object {
        private const val TAG = "MainActivity"
        private const val MAX_PAGE = 20
        private const val CURRENT_PAGE = "CURRENT_PAGE"
        private const val CURRENT_QUERY = "CURRENT_QUERY"
        private const val SERIAL_TAG = "SERIAL_TAG"
    }


    private lateinit var pageLoadManager: PageLoadManager
    @Inject lateinit var presenter: MainPresenter
    private lateinit var mNavDrawler: DrawerLayout
    override var isLoading: Boolean = false
    private var currentQuery: String = ""
    private lateinit var liveData: AdapterLiveData
    private val listAdapter: ListItemAdapter get() = recyclerList.adapter as ListItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.nav_activity_list)
        super.onCreate(savedInstanceState)

//        onRetainCustomNonConfigurationInstance()
        setSupportActionBar(bookmarkToolbar)

        setupLiveData()
        setupNavDrawler()
        setUpRecycler()
        setupPageLoadManager(savedInstanceState)

        mainFab.setOnClickListener {
            currentQuery = ""
            listAdapter.clear()
            mSearchView.clearQuery()
            pageLoadManager.currentPage = PageLoadManager.INIT_START_PAGE_DEFAULT_INDEX
            presenter.loadData(currentQuery)
        }

        setupSwipeRefresh()
        setupSearchView()

    }

    private fun setupSwipeRefresh() {
        listSwipeRefreshLayout?.setColorSchemeColors(resources.getColor(R.color.swipeRefreshRed),
                resources.getColor(R.color.swipeRefreshBlue),
                resources.getColor(R.color.swipeRefreshGreen))
        listSwipeRefreshLayout?.setOnRefreshListener(this)
    }

    private fun setupSearchView() {
        mSearchView?.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSearchAction(currentQuery: String?) {
                this@MainActivity.currentQuery = currentQuery ?: ""

                listAdapter.clear()
                pageLoadManager.currentPage = 1
                pageLoadManager.loadAction = { pageNumber: Int ->
                    presenter.loadData(this@MainActivity.currentQuery, pageNumber)
                }

                pageLoadManager.load()
            }

            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    private fun setupPageLoadManager(savedInstanceState: Bundle?) {
        pageLoadManager = PageLoadManager(recyclerList.layoutManager as LinearLayoutManager, this@MainActivity, recyclerList,
                { pageNumber: Int ->
                    presenter.loadData("", page = pageNumber)
                })

        if (savedInstanceState == null) {
            pageLoadManager.load()
        }
    }

    private fun setupLiveData() {
        liveData = ViewModelProviders.of(this).get(AdapterLiveData::class.java)
    }

    private fun setupNavDrawler() {
        mNavDrawler = findViewById<DrawerLayout>(R.id.drawerLayout) as DrawerLayout
        navView.setNavigationItemSelectedListener(this)
        mSearchView.attachNavigationDrawerToMenuButton(mNavDrawler)
        mSearchView.setSearchHint(getString(R.string.search))
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        timd("MainActivity:onNavigationItemSelected:MenuItem = $item")
        if (item.itemId == R.id.nav_list_history) {
            val i = Intent(this, HistoryActivity::class.java)
            mNavDrawler.closeDrawers()
            startActivity(i)

            return true
        } else if (item.itemId == R.id.nav_list_sources) {
            val i = Intent(this, SourceActivity::class.java)
            mNavDrawler.closeDrawers()
            startActivity(i)

            return true
        } else if (item.itemId == R.id.nav_list_bookmarks) {
            val i = Intent(this, BookmarkActivity::class.java)
            mNavDrawler.closeDrawers()
            startActivity(i)

            return true
        }

        return true
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(CURRENT_PAGE, pageLoadManager.currentPage)
        outState?.putString(CURRENT_QUERY, currentQuery)
        liveData.liveData = listAdapter.data
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        pageLoadManager.currentPage = savedInstanceState?.getInt(CURRENT_PAGE) ?: 1
        currentQuery = savedInstanceState?.getString(CURRENT_QUERY) ?: ""
        listAdapter.mData = liveData.liveData?.toMutableList()
        listAdapter.notifyDataSetChanged()
    }

    override fun initRecycler(): RecyclerView {
        return recyclerList
    }

    override fun handleSwipeAction(position: Int?) {
        val removeItem = listAdapter.data[position ?: throw Exception("Data access denied with index = $position")]

        listAdapter.removeAt(position)

        val snackBar = Snackbar.make(mainListLayout, "Removed from list!", Snackbar.LENGTH_LONG)

        snackBar.setAction(getString(R.string.cancel)) {
            listAdapter.restoreAt(position, removeItem)
        }
        snackBar.setActionTextColor(Color.YELLOW)
        snackBar.show()
    }

    private val fabClickListener = fun() {
        currentQuery = ""
        listAdapter.clear()
        mSearchView.clearQuery()
        pageLoadManager.currentPage = PageLoadManager.INIT_START_PAGE_DEFAULT_INDEX
        presenter.loadData(currentQuery)
    }

    private fun setUpRecycler() {
        recyclerList.adapter = ListItemAdapter(mutableListOf(), this)
        recyclerList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        mainFab.attachToRecycler(recyclerList, fabClickListener)
    }

    override fun onRefresh() {
        presenter.loadData(query = this.currentQuery)
    }

    override fun onStartLoading() {
        isLoading = true
        listSwipeRefreshLayout.isRefreshing = true
    }

    override fun onError(message: String) {
        isLoading = false
    }

    override fun onComplete() {
        isLoading = false
        if (listSwipeRefreshLayout?.isRefreshing == true) {
            listSwipeRefreshLayout?.isRefreshing = false
        }
    }

    override fun showData(data: List<Article>) {
        listAdapter.addAll(data)
    }

    override fun onClick(pos: Int) {
        Tim.d("MainActivity:onClick:position = $pos")
        val article = (recyclerList.adapter as ListItemAdapter).data[pos]

        presenter.save(convertToHistory(article))

        startBrowser(article.url ?: throw Exception("The url must not be null."))
    }

    override fun changeListener(position: Int) {
        val article = (recyclerList.adapter as ListItemAdapter).data[position]
        if (article.isBookmarked) {
            Toast.makeText(this, "Bookmark is added", Toast.LENGTH_SHORT).show()
            presenter.save(convertToBookmark(article))
        } else {
            Toast.makeText(this, "Bookmark is removed", Toast.LENGTH_SHORT).show()
        }
    }

    private class AdapterLiveData : ViewModel() {
        var liveData: List<Article>? = null
    }
}
