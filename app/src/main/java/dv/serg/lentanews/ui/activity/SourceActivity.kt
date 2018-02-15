package dv.serg.lentanews.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import dv.serg.lentanews.R
import dv.serg.lentanews.adapter.SourceAdapter
import dv.serg.lentanews.dao.room.entity.SourceItem
import dv.serg.lentanews.dao.room.entity.getPredefinedSources
import dv.serg.lentanews.presenter.SourcePresenter
import dv.serg.lentanews.util.Tim
import kotlinx.android.synthetic.main.activity_source.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.coordinator_source_layout.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import timber.log.Timber
import javax.inject.Inject


class SourceActivity : AppCompatActivity(), ActionMode.Callback, SourceAdapter.ActionListener {

    @Inject lateinit var presenter: SourcePresenter
    private var selSources: MutableList<SourceItem>? = ArrayList()

    override fun action(selectedCount: Int) {
        Tim.d("SourceActivity:action:selectedCount = $selectedCount")

        if (mActionMode == null) {
            mActionMode = startSupportActionMode(this)
        }

        mActionMode?.title = "Selected sources $selectedCount"
        selSources?.clear()
        selSources?.addAll(sourceAdapter?.getSelectedSources()?.asIterable() ?: throw Exception("Iterable exception."))
//        selSources = sourceAdapter?.getSelectedSources()?.toMutableList()?
        Tim.d("SourceActivity:selSources = ${selSources}")

        if (selectedCount == 0) {
            mActionMode?.finish()
        }
    }

    override fun onActionItemClicked(p0: ActionMode?, item: MenuItem?): Boolean {
        Timber.d("SourceActivity:action")

        when (item?.itemId) {
            R.id.action_submit -> {
                Tim.d("onActionItemClicked")
                mActionMode?.finish()
                mActionMode = null

                doAsync {
                    deleteAll()

                    if (selSources != null) {
//                        val selectedSources: List<SourceItem>? = sourceAdapter?.getSelectedSources()

                        Tim.d("selectedSources = $selSources")
                        save(selSources?.toList() ?: throw Exception("Exception TODO"))
                    }

                    uiThread {
                        sourceAdapter?.clear()
                        startActivity(Intent(this@SourceActivity, MainActivity::class.java))
                    }
                }

            }
        }

        return true
    }


    private fun deleteAll() {
        presenter.dao.deleteAll()
    }

    // todo refactor it to di and presenter too
    // comes nothing
    private fun save(objs: List<SourceItem>) {
        presenter.saveAll(objs)
    }

    override fun onCreateActionMode(p0: ActionMode?, menu: Menu?): Boolean {
        Tim.d("SourceActivity:onCreateActionMode")
        p0?.menuInflater?.inflate(R.menu.action_source_mode, menu)
//        supportActionBar?.hide()

        return true
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
        Tim.d("SourceActivity:onPrepareActionMode")
        return false
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        Tim.d("SourceActivity:onDestroyActionMode:recyclerList.adapter = ${recyclerList}")
        (sourceRecyclerView.adapter as SourceAdapter).clear()

        mActionMode = null
//        supportActionBar?.show()

    }

    private var mActionMode: ActionMode? = null

    private var sourceAdapter: SourceAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coordinator_source_layout)

        setSupportActionBar(sourceToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        // todo to string resource
        supportActionBar?.title = "Select sources"

        sourceRecyclerView.adapter = SourceAdapter(getPredefinedSources(), this)

        sourceRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        sourceAdapter = sourceRecyclerView.adapter as SourceAdapter?

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}

