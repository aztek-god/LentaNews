package dv.serg.lentanews.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import dv.serg.lentanews.R
import dv.serg.lentanews.R.id.recyclerList
import dv.serg.lentanews.adapter.SourceAdapter
import dv.serg.lentanews.pojo.SourceItem
import dv.serg.lentanews.pojo.getPredefinedSources
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_source.*
import kotlinx.android.synthetic.main.content_list.*
import timber.log.Timber

typealias Tim = Timber

class SourceActivity : AppCompatActivity(), ActionMode.Callback, SourceAdapter.ActionListener {

    override fun action(selectedCount: Int) {
        Tim.d("SourceActivity:action:selectedCount = $selectedCount")

        if (mActionMode == null) {
            mActionMode = startActionMode(this)
        }

        mActionMode?.title = "Selected sources $selectedCount"

        if (selectedCount == 0) {
            mActionMode?.finish()
        }
    }

    override fun onActionItemClicked(p0: ActionMode?, item: MenuItem?): Boolean {
        Timber.d("SourceActivity:action")

        when (item?.itemId) {
            R.id.action_submit -> {
                mActionMode?.finish()
                mActionMode = null

                // todo rewrite in dao implementation
                deleteAllFromRealm()

                if (sourceAdapter?.getSelectedSources() != null) {
                    val selectedSources: List<SourceItem>? = sourceAdapter?.getSelectedSources()

                    Tim.d("selectedSources = $selectedSources")
                    if (selectedSources != null) {
                        saveToRealm(selectedSources)
                    }
                }

                sourceAdapter?.clear()

                startActivity(Intent(this, ListActivity::class.java))
            }
        }

        return true
    }

    private fun deleteAllFromRealm() {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.deleteAll()
        }
    }

    // todo refactor it to di and presenter too
    // comes nothing
    private fun saveToRealm(objs: List<SourceItem>) {

        Tim.d("SourceActivity:saveToRealm = ${objs.joinToString(separator = ";", transform = {
            "sourceId = ${it.sourceName}, sourceTitle = ${it.sourceTitle}"
        })}")

        val realmInstance = Realm.getDefaultInstance()

//        realmInstance.beginTransaction()

        realmInstance.executeTransaction {
            it.copyToRealm(objs)
        }

//        realmInstance.copyToRealm(objs)
//
//        realmInstance.commitTransaction()
    }

    override fun onCreateActionMode(p0: ActionMode?, menu: Menu?): Boolean {
        Tim.d("SourceActivity:onCreateActionMode")
        p0?.menuInflater?.inflate(R.menu.action_source_mode, menu)
        return true
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
        Tim.d("SourceActivity:onPrepareActionMode")
        return false
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        Tim.d("SourceActivity:onDestroyActionMode:recyclerList.adapter = ${recyclerList}")

//        (sourceRecyclerView.adapter as SourceAdapter).clear()

        mActionMode = null
    }

    private var mActionMode: ActionMode? = null

    private var sourceAdapter: SourceAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source)


        sourceRecyclerView.adapter = SourceAdapter(getPredefinedSources(), this)

        sourceRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        sourceAdapter = sourceRecyclerView.adapter as SourceAdapter?



        floatingActionButton.setOnClickListener {
            Tim.d("sourceAdapter:size = ${sourceAdapter?.getSelectedSources()?.size}")

            sourceAdapter?.clear()
        }
    }

}

