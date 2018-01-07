package dv.serg.lentanews.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import dv.serg.lentanews.R
import dv.serg.lentanews.adapter.BookmarkAdapter
import dv.serg.lentanews.pojo.Bookmark
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_bookmark.*
import kotlinx.android.synthetic.main.content_bookmark.*
import timber.log.Timber

class BookmarkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)
        setSupportActionBar(toolbar)

        bookmarkRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val result: MutableList<Bookmark> = ArrayList()
        val realmResult: RealmResults<Bookmark> = Realm.getDefaultInstance().where(Bookmark::class.java).findAllAsync()

        realmResult.toCollection(result)

        bookmarkRecycler.adapter = BookmarkAdapter(result, { v, pos ->
            Tim.d("v = $v, pos = $pos")
            val url = (bookmarkRecycler.adapter as BookmarkAdapter).data[pos].url
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }, { _, pos ->
            Tim.d("Long click occurred at position = $pos")
        })

        (bookmarkRecycler.adapter as BookmarkAdapter)

        bookmarkRecycler.adapter.notifyDataSetChanged()

        Timber.d("BookmarkActivity:onCreate:result = $result")
    }
}
