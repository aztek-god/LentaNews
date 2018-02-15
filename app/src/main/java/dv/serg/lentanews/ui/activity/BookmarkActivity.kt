package dv.serg.lentanews.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import dv.serg.lentanews.R
import dv.serg.lentanews.abstr.AbstractSwipeActivity
import dv.serg.lentanews.dao.room.entity.Bookmark
import dv.serg.lentanews.presenter.BookmarkPresenter
import dv.serg.lentanews.ui.adapter.BookmarkAdapter
import dv.serg.lentanews.util.attachToRecycler
import dv.serg.lentanews.util.setupActionBar
import kotlinx.android.synthetic.main.activity_bookmark.*
import kotlinx.android.synthetic.main.content_bookmark.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class BookmarkActivity : AbstractSwipeActivity(), BookmarkPresenter.BookmarkView {

    @Inject lateinit var presenter: BookmarkPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_bookmark)
        super.onCreate(savedInstanceState)

        setupActionBar(bookmarkToolbar)

        bookmarkRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

//        bookmarkRecycler.adapter = BookmarkAdapter(clickListener = { v, pos ->
//            val url = (bookmarkRecycler.adapter as BookmarkAdapter).mutableData[pos].url
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.oldData = Uri.parse(url)
//            startActivity(intent)
//        }, longListener = { _, pos ->
//            Tim.d("Long click occurred at position = $pos")
//        })

        fab.attachToRecycler(bookmarkRecycler) {
            val bookmarkAdapter = bookmarkRecycler.adapter as BookmarkAdapter
            val restoreData: MutableList<Bookmark> = ArrayList()

            doAsync {
                bookmarkAdapter.mutableData.toCollection(restoreData)
            }

            val snackBar = Snackbar.make(activityBookmarkRoot, getString(R.string.bookmarks_deleted),
                    Snackbar.LENGTH_LONG)

            presenter.removeAll()
            presenter.loadData()

            snackBar.setAction(getString(R.string.cancel)) {
//                bookmarkAdapter.mData = restoreData
                bookmarkAdapter.notifyDataSetChanged()
                presenter.dao.insertAll(restoreData)
                presenter.loadData()
            }

//            snackBar.dismiss()

            snackBar.show()
        }

        presenter.loadData()
    }

    override fun showData(data: List<Bookmark>) {
        if (!data.isEmpty()) {
            fab.show()
        } else {
            fab.hide()
        }

        val bookmarkAdapter = bookmarkRecycler.adapter as BookmarkAdapter
//        bookmarkAdapter.mData = oldData.toMutableList()
        bookmarkAdapter.notifyDataSetChanged()
    }

    override fun showError(errorMessage: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onComplete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initRecycler(): RecyclerView {
        return bookmarkRecycler
    }

    override fun handleSwipeAction(position: Int?) {
        val bookmarkAdapter = bookmarkRecycler.adapter as BookmarkAdapter
        val restorePos: Int = position ?: throw Exception("Exception")
        val restoreItem = bookmarkAdapter.mData[restorePos]
        presenter.remove(restoreItem)
        bookmarkAdapter.mData.removeAt(restorePos)

        val snackBar = Snackbar.make(bookmarkLayout, "Bookmark deleted.", Snackbar.LENGTH_LONG)

        snackBar.setAction("UNDO") {
            bookmarkAdapter.mData.add(restorePos, restoreItem)
            bookmarkAdapter.notifyDataSetChanged()
            presenter.insert(restoreItem)
        }


        snackBar.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
