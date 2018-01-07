package dv.serg.lentanews.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MenuItem
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import dv.serg.lentanews.R
import dv.serg.lentanews.adapter.ListItemAdapter
import dv.serg.lentanews.contract.MainContract
import dv.serg.lentanews.holder.ListItemHolder
import dv.serg.lentanews.pojo.Article
import dv.serg.lentanews.pojo.History
import dv.serg.lentanews.presenter.MainPresenter
import dv.serg.lentanews.util.Injectable
import dv.serg.lentanews.util.PaginationScrollListener
import dv.serg.lentanews.util.RetainAbstractActivity
import dv.serg.lentanews.util.UiAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.content_list.*
import kotlinx.android.synthetic.main.nav_activity_list.*
import timber.log.Timber
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class ListActivity : RetainAbstractActivity(), Injectable, MainContract.MainView<List<Article>>, SwipeRefreshLayout.OnRefreshListener,
        NavigationView.OnNavigationItemSelectedListener {


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
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


    companion object {
        private const val TAG = "MainActivity"
        private const val MAX_PAGE = 20
        private const val CURRENT_PAGE = "CURRENT_PAGE"
        private const val CURRENT_QUERY = "CURRENT_QUERY"
        private const val SERIAL_TAG = "SERIAL_TAG"
    }

    @Inject lateinit var presenter: MainPresenter

    private lateinit var mNavDrawler: DrawerLayout

    private lateinit var uiAdapter: UiAdapter<Article>

    private var isLoading: Boolean = false
    private var currentQuery: String = ""
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_activity_list)
        setSupportActionBar(toolbar)

        mNavDrawler = findViewById<DrawerLayout>(R.id.drawerLayout) as DrawerLayout

        mSearchView.attachNavigationDrawerToMenuButton(mNavDrawler)

        navView.setNavigationItemSelectedListener(this)

        initRecycler()

        if (savedInstanceState == null) {
            presenter.loadData(currentQuery)
        }

        listSwipeRefreshLayout?.setColorSchemeColors(resources.getColor(R.color.swipeRefreshRed),
                resources.getColor(R.color.swipeRefreshBlue),
                resources.getColor(R.color.swipeRefreshGreen))
        listSwipeRefreshLayout?.setOnRefreshListener(this)

        mSearchView?.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSearchAction(currentQuery: String?) {
                this@ListActivity.currentQuery = currentQuery ?: ""
                this@ListActivity.currentPage = 1
                uiAdapter.clear()
                presenter.loadData(this@ListActivity.currentQuery)
            }

            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Timber.d("onSaveInstanceState")
        saveAdapter(recyclerList.adapter)
        outState?.putInt(CURRENT_PAGE, currentPage)
        outState?.putString(CURRENT_QUERY, currentQuery)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.d("onRestoreInstanceState")
        recyclerList.adapter = loadAdapter()
        uiAdapter = recyclerList.adapter as? UiAdapter<Article> ?: throw Exception("Something went wrong")
        currentPage = savedInstanceState?.getInt(CURRENT_PAGE) ?: 1
        currentQuery = savedInstanceState?.getString(CURRENT_QUERY) ?: ""
    }


    private fun saveHistory(article: Article?) {
        val history = History(sourceFrom = article?.sourceId ?: "", title = article?.title ?: "", shortDesc = article?.description ?: "",
                dateTime = article?.publishedAt ?: "", url = article?.url ?: "",
                visited = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().time))

        val realmInstance: Realm = Realm.getDefaultInstance()

        realmInstance.beginTransaction()
        realmInstance.copyToRealm(history)
        realmInstance.commitTransaction()
    }

    private fun initRecycler() {
        uiAdapter = ListItemAdapter { it: ListItemHolder ->
            saveHistory(uiAdapter[it.adapterPosition])
            val url = uiAdapter[it.adapterPosition].url
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        recyclerList.adapter = uiAdapter as RecyclerView.Adapter<*>

        recyclerList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        recyclerList.addOnScrollListener(
                object : PaginationScrollListener(recyclerList.layoutManager as LinearLayoutManager) {
                    override fun loadMoreItems() {
                        Timber.d("initRecycler:recyclerList.addOnScrollListener:loadMoreItems:currentPage = $currentPage")
                        presenter.loadData(this@ListActivity.currentQuery, ++currentPage)
                    }

                    override fun getTotalPageCount(): Int = MAX_PAGE

                    override fun isLastPage(): Boolean = currentPage == MAX_PAGE

                    override fun isLoading(): Boolean = isLoading
                }
        )


        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)), Serializable {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                val removeIndex = viewHolder?.adapterPosition ?: 0
                val removeItem = uiAdapter[removeIndex]
                uiAdapter.removeAt(viewHolder?.adapterPosition ?: 0)
                uiAdapter.notifyDataChanged()


                val snackBar = Snackbar.make(activityListLayout, "Removed from list!", Snackbar.LENGTH_LONG)

                snackBar.setAction("UNDO") {
                    uiAdapter.restoreAt(removeIndex, removeItem)
                    uiAdapter.notifyDataChanged()
                }
                snackBar.setActionTextColor(Color.YELLOW)
                snackBar.show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)

        itemTouchHelper.attachToRecyclerView(recyclerList)
    }

    override fun onRefresh() {
        this.currentPage = 1
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
        Timber.d("showData = ${data.toString()}")

        uiAdapter.appendData(data)
        uiAdapter.notifyDataChanged()
    }
}
