package dv.serg.lentanews.ui.activity

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import dv.serg.lentanews.R
import dv.serg.lentanews.pojo.Article
import dv.serg.lentanews.ui.adapter.BookmarkAdapter
import dv.serg.lentanews.ui.adapter.CommonAdapter
import dv.serg.lentanews.ui.fragment.ListFragment
import dv.serg.lentanews.ui.test.TestViewHolder
import dv.serg.lentanews.util.Tim
import dv.serg.lentanews.util.attachToRecycler
import dv.serg.lentanews.util.replaceFragment

import kotlinx.android.synthetic.main.activity_overview.*
import kotlinx.android.synthetic.main.test_list.*

class OverviewActivity : AppCompatActivity(), ListFragment.OnFragmentInteractionListener {

    private lateinit var commonAdapter: CommonAdapter<String, TestViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_list)
        setSupportActionBar(toolbar)

        replaceFragment(R.id.testFragment, ListFragment.newInstance("", ""))
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


