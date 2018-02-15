package dv.serg.lentanews.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import dv.serg.lentanews.R
import dv.serg.lentanews.abstr.AbstractSwipeActivity
import dv.serg.lentanews.adapter.HistoryAdapter
import dv.serg.lentanews.dao.room.entity.Bookmark
import dv.serg.lentanews.dao.room.entity.History
import dv.serg.lentanews.presenter.HistoryPresenter
import dv.serg.lentanews.util.Tim
import kotlinx.android.synthetic.main.coordinator_source_layout.*
import kotlinx.android.synthetic.main.history_activity_layout.*
import kotlinx.android.synthetic.main.history_list.*
import org.jetbrains.anko.doAsync
import timber.log.Timber
import javax.inject.Inject

class HistoryActivity : AbstractSwipeActivity(), HistoryPresenter.HistoryView {

    @Inject lateinit var presenter: HistoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.history_activity_layout)
        super.onCreate(savedInstanceState)

        setSupportActionBar(historyToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        presenter.loadData()

        val destinationList: MutableList<History> = ArrayList()

        Timber.d("destinationList:size = ${destinationList.size}")

        historyRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        historyRecycler.adapter = HistoryAdapter(destinationList, clickListener = { _, pos ->
            val url: String = (historyRecycler.adapter as HistoryAdapter).data[pos].url
            startBrowser(url)
        })

        fab.setOnClickListener {
            val historyAdapter = historyRecycler.adapter as HistoryAdapter
            val restoreData: MutableList<History> = ArrayList()

            doAsync {
                historyAdapter.data.toCollection(restoreData)
            }

            presenter.removeAll()
            presenter.loadData()

            val snackBar = Snackbar.make(historyListLayout, "History deleted.", Snackbar.LENGTH_LONG)

            snackBar.setAction(getString(R.string.cancel)) {
                historyAdapter.mData = restoreData
                historyAdapter.notifyDataSetChanged()
                presenter.dao.insertAll(restoreData)
                presenter.loadData()
            }

            snackBar.show()
        }
    }

    override fun showData(data: List<History>) {
        // todo refactor
        if (!data.isEmpty()) {
            fab.show()
        } else {
            fab.hide()
        }
        val historyAdapter = historyRecycler.adapter as HistoryAdapter
        historyAdapter.mData = data.toMutableList()
        historyAdapter.notifyDataSetChanged()
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(this, "The error occurred: $errorMessage.", Toast.LENGTH_SHORT).show()
    }

    override fun onComplete() {
        Tim.d("onComplete() point destination.")
    }

    private fun startBrowser(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    override fun initRecycler(): RecyclerView {
        return historyRecycler
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun handleSwipeAction(position: Int?) {
        val historyAdapter = historyRecycler.adapter as HistoryAdapter
        val restorePos: Int = position ?: throw Exception("Exception occured at index = ${position}")
        val restoreItem = historyAdapter.mData[restorePos]
        presenter.remove(historyAdapter.data[restorePos])
        historyAdapter.mData.removeAt(restorePos)

        val snackBar = Snackbar.make(historyListLayout, "Bookmark deleted.", Snackbar.LENGTH_LONG)

        snackBar.setAction("UNDO") {
            historyAdapter.mData.add(restorePos, restoreItem)
            historyAdapter.notifyDataSetChanged()
            presenter.insert(restoreItem)
        }


        snackBar.show()
    }
}














