package dv.serg.lentanews.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import dv.serg.lentanews.R
import dv.serg.lentanews.R.id.*
import dv.serg.lentanews.lifecycle.RetainAbstractActivity
import dv.serg.lentanews.pojo.Article
import dv.serg.lentanews.ui.adapter.CommonAdapter
import dv.serg.lentanews.ui.test.TestViewHolder
import dv.serg.lentanews.util.Tim

import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.content_list.*

class ListActivity : RetainAbstractActivity() {
    override fun recoveryAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        Tim.d("recoveryAdapter")
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var listAdapter: CommonAdapter<String, TestViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        listAdapter = CommonAdapter(R.layout.test_recycler_item, { view -> TestViewHolder(view) })
        listRecycler.adapter = listAdapter
        listRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        listAdapter.updateData(listOf("a", "b", "c"))



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
