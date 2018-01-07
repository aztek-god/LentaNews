package dv.serg.lentanews.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import dv.serg.lentanews.R
import dv.serg.lentanews.adapter.HistoryAdapter
import dv.serg.lentanews.pojo.History
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.history_list.*
import timber.log.Timber

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_activity_layout)

        val realmResult: RealmResults<History> = Realm.getDefaultInstance().where(History::class.java).findAllAsync()

        val destinationList: MutableList<History> = ArrayList()
        realmResult.toCollection(destinationList)

        Timber.d("destinationList:size = ${destinationList.size}")

        historyRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        historyRecycler.adapter = HistoryAdapter(destinationList)
        historyRecycler.adapter.notifyDataSetChanged()
    }
}
